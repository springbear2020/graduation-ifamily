package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.client.acl.AclFeignClient;
import cn.edu.whut.springbear.ifamily.common.constant.RedisConstants;
import cn.edu.whut.springbear.ifamily.common.constant.UserMessageConstants;
import cn.edu.whut.springbear.ifamily.common.enumerate.DeleteStatusEnum;
import cn.edu.whut.springbear.ifamily.common.enumerate.EnableStatusEnum;
import cn.edu.whut.springbear.ifamily.common.exception.IllegalConditionException;
import cn.edu.whut.springbear.ifamily.common.exception.IllegalStatusException;
import cn.edu.whut.springbear.ifamily.model.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import cn.edu.whut.springbear.ifamily.security.util.JwtUtils;
import cn.edu.whut.springbear.ifamily.user.mapper.UserMapper;
import cn.edu.whut.springbear.ifamily.user.pojo.dto.SecurityUserDetailsDTO;
import cn.edu.whut.springbear.ifamily.user.pojo.po.UsernameUpdateLogDO;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserLoginQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserResetQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.UserVO;
import cn.edu.whut.springbear.ifamily.user.service.UserLoginLogService;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import cn.edu.whut.springbear.ifamily.user.service.UsernameUpdateLogService;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService, UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AclFeignClient aclFeignClient;
    @Autowired
    private UserLoginLogService userLoginLogService;
    @Autowired
    private UsernameUpdateLogService usernameUpdateLogService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserDO user = this.baseMapper.selectOne(queryWrapper);

        if (user == null) {
            // [401]UsernameNotFoundException -> AuthenticationException -> RestfulUnauthorizedEntryPoint
            throw new UsernameNotFoundException(UserMessageConstants.USER_NOT_EXISTS);
        }

        // 检查用户账号状态
        if (EnableStatusEnum.DISABLE.getCode().equals(user.getStatus())) {
            // [401] RestfulUnauthorizedEntryPoint
            throw new UsernameNotFoundException(UserMessageConstants.ILLEGAL_USER_STATUS);
        }

        // 查询当前用户下拥有的权限列表，提供给安全框架鉴权使用
        List<PermissionDO> permissions = this.aclFeignClient.listPermissionsOfUser(user.getId());
        permissions = permissions == null ? new ArrayList<>() : permissions;
        return new SecurityUserDetailsDTO(user, permissions);
    }

    @Override
    public String login(UserLoginQuery userLoginQuery) {
        // 根据用户名、手机、邮箱查询用户，验证用户是否存在
        String account = userLoginQuery.getAccount();
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", account).or().eq("email", account).or().eq("username", account);
        UserDO user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new IllegalConditionException(UserMessageConstants.USER_NOT_EXISTS);
        }

        // 客户端请求的登录类型：[0]密码登录 [1]验证码登录
        final int passwordLogin = 0;
        Integer loginType = userLoginQuery.getLoginType();
        if (loginType == passwordLogin) {
            // 验证密码正确性
            if (!this.passwordEncoder.matches(userLoginQuery.getPassword(), user.getPassword())) {
                throw new IllegalConditionException(UserMessageConstants.ERROR_PASSWORD);
            }
        } else {
            // 检验当前验证码的正确性
            this.validateVerifyCode(account, userLoginQuery.getCode());
        }

        // 检查用户账号状态
        if (EnableStatusEnum.DISABLE.getCode().equals(user.getStatus())) {
            throw new IllegalStatusException(UserMessageConstants.ILLEGAL_USER_STATUS);
        }

        // 查询当前用户的系统权限，将当前用户信息注入到安全框架中
        List<PermissionDO> permissions = this.aclFeignClient.listPermissionsOfUser(user.getId());
        permissions = permissions == null ? new ArrayList<>() : permissions;
        SecurityUserDetailsDTO userDetails = new SecurityUserDetailsDTO(user, permissions);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 保存用户登录记录
        this.userLoginLogService.create(user.getId());
        // 更新上次登录时间
        UserDO userDO = new UserDO();
        userDO.setId(user.getId());
        userDO.setLastLogin(new Date());
        this.baseMapper.updateById(userDO);

        return JwtUtils.create("username", user.getUsername());
    }

    @Override
    public String register(UserResetQuery userResetQuery) {
        UserDO userDO = new UserDO();
        String account = userResetQuery.getAccount();

        // 正则表达式匹配客户端的注册方式是手机注册还是邮箱注册
        String phoneRegexp = "^1[3456789]\\d{9}$";
        String emailRegexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (ReUtil.isMatch(phoneRegexp, account)) {
            // 手机方式注册，验证手机号是否已被占用
            userDO.setPhone(account);
            this.verifyPhoneOrEmailExistence("phone", account);
        } else if (ReUtil.isMatch(emailRegexp, account)) {
            // 邮箱方式注册，验证邮箱是否已被占用
            userDO.setEmail(account);
            this.verifyPhoneOrEmailExistence("email", account);
        } else {
            throw new IllegalConditionException("账号格式不正确，请重新输入");
        }

        // 验证用户输入的验证码是否正确
        this.validateVerifyCode(account, userResetQuery.getCode());

        // 设置用户注册所需的必要信息，用户名唯一、密码加密存储
        Date date = new Date();
        userDO.setUsername(IdUtil.simpleUUID());
        userDO.setPassword(this.passwordEncoder.encode(userResetQuery.getPassword()));
        userDO.setCreated(date);
        userDO.setModified(date);
        userDO.setLastLogin(date);
        userDO.setStatus(EnableStatusEnum.ENABLE.getCode());
        userDO.setDeleted(DeleteStatusEnum.UNDELETED.getCode());
        this.baseMapper.insert(userDO);

        return JwtUtils.create("username", userDO.getUsername());
    }

    @Override
    public boolean reset(UserResetQuery userResetQuery) {
        // 根据手机号或邮箱查询用户信息是否存在
        String account = userResetQuery.getAccount();
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", account).or().eq("email", account);
        UserDO user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new IllegalConditionException(UserMessageConstants.USER_NOT_EXISTS);
        }

        // 判断新旧密码是否一致
        if (this.passwordEncoder.matches(userResetQuery.getPassword(), user.getPassword())) {
            throw new IllegalConditionException("新旧密码相同，请重新输入");
        }

        // 检验验证码是否正确
        this.validateVerifyCode(account, userResetQuery.getCode());

        // 更新用户登录密码
        String encodedNewPassword = this.passwordEncoder.encode(userResetQuery.getPassword());
        UserDO userDO = new UserDO();
        userDO.setId(user.getId());
        userDO.setPassword(encodedNewPassword);
        return this.baseMapper.updateById(userDO) == 1;
    }

    @Override
    public UserVO currentUser() {
        UserDO userDO = this.getCurrentUser();
        // 手机号脱敏，隐藏中间四位为 *
        userDO.setPhone(DesensitizedUtil.mobilePhone(userDO.getPhone()));
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
    }

    @Override
    public boolean updateSimpleProfile(UserQuery userQuery) {
        UserDO user = this.getCurrentUser();
        UserDO userDO = new UserDO();
        // 更新头像地址 || 用户昵称 || 个性签名
        BeanUtils.copyProperties(userQuery, userDO);
        userDO.setId(user.getId());
        return this.baseMapper.updateById(userDO) == 1;
    }

    @Override
    public boolean updateUsername(String username, String password) {
        UserDO user = this.getCurrentUser();

        // 新用户名与旧用户名一致，不允许修改
        if (user.getUsername().equals(username)) {
            throw new IllegalConditionException("新旧 UID 一致，请重新输入");
        }

        // 验证密码是否正确
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalConditionException(UserMessageConstants.ERROR_PASSWORD);
        }

        // 查询当前用户用户名的上次更新时间，一年内只允许更新一次
        UsernameUpdateLogDO latest = this.usernameUpdateLogService.getLatest(user.getId());
        if (latest != null) {
            Date lastUpdate = latest.getCreated();
            long days = DateUtil.between(lastUpdate, new Date(), DateUnit.DAY);
            final int yearDays = 365;
            if (days < yearDays) {
                throw new IllegalConditionException("UID 一年内只能修改一次，上次修改时间：" + DateUtil.formatDate(lastUpdate));
            }
        }

        // 保存用户最新的用户名修改记录
        this.usernameUpdateLogService.create(user.getUsername(), username, user.getId());
        // 更新用户名
        UserDO userDO = new UserDO();
        userDO.setUsername(username);
        userDO.setId(user.getId());

        return this.baseMapper.updateById(userDO) == 1;
    }

    @Override
    public boolean updateEmail(String email, String code) {
        // 验证新旧邮箱是否一致，注意旧邮箱为 null
        UserDO user = this.getCurrentUser();
        if (user.getEmail() != null && email.equals(user.getEmail())) {
            throw new IllegalConditionException("新旧邮箱一致，请重新输入");
        }

        // 验证邮箱是否已被占用
        this.verifyPhoneOrEmailExistence("email", email);
        // 验证用户输入的验证码是否正确
        this.validateVerifyCode(email, code);

        // 更新用户邮箱地址
        UserDO userDO = new UserDO();
        userDO.setEmail(email);
        userDO.setId(user.getId());
        return this.baseMapper.updateById(userDO) == 1;
    }

    @Override
    public boolean updatePhone(String phone, String code) {
        // 验证新旧手机号是否一致，注意旧手机号为 null
        UserDO user = this.getCurrentUser();
        if (user.getPhone() != null && phone.equals(user.getPhone())) {
            throw new IllegalConditionException("新旧手机号一致，请重新输入");
        }

        // 验证手机号是否已被占用
        this.verifyPhoneOrEmailExistence("phone", phone);
        // 验证用户输入的验证码是否正确
        this.validateVerifyCode(phone, code);

        // 更新用户手机号
        UserDO userDO = new UserDO();
        userDO.setPhone(phone);
        userDO.setId(user.getId());
        return this.baseMapper.updateById(userDO) == 1;
    }

    @Override
    public boolean userLogout(String password) {
        UserDO user = this.getCurrentUser();
        // 验证输入的密码是否正确
        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalConditionException(UserMessageConstants.ERROR_PASSWORD);
        }

        // 更新用户账号启用状态、删除状态
        return this.baseMapper.deleteById(user.getId()) == 1;
    }

    @Override
    public UserDO getUserByUsername(String username) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return this.baseMapper.selectOne(queryWrapper);
    }

    /**
     * 获取当前请求登录系统的用户信息
     */
    UserDO getCurrentUser() {
        SecurityUserDetailsDTO userDetailsDTO = (SecurityUserDetailsDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetailsDTO == null) {
            throw new IllegalStatusException(UserMessageConstants.UNAUTHORIZED);
        }

        // 根据用户查询用户信息
        String username = userDetailsDTO.getUsername();
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserDO userDO = this.baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            throw new IllegalStatusException(UserMessageConstants.USER_NOT_EXISTS);
        }
        return userDO;
    }

    /**
     * 验证手机号或邮箱在用户表中的存在性
     *
     * @param column  email | phone
     * @param content 需要验证的内容
     */
    private void verifyPhoneOrEmailExistence(String column, String content) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(column, content);
        UserDO userDO = this.baseMapper.selectOne(queryWrapper);
        String chinese = "email".equals(column) ? "邮箱地址" : "手机号";
        if (userDO != null) {
            throw new IllegalConditionException(chinese + "已被占用，请重新输入");
        }
    }

    /**
     * 校验验证码正确性，将传入的验证码与 Redis 中存储的验证码进行比对
     *
     * @param account 需要验证的邮箱 | 手机
     * @param code    需要检验的验证码
     */
    private void validateVerifyCode(String account, String code) {
        String key = RedisConstants.VERIFY_CODE_PREFIX + account;
        String codeFromRedis = redisTemplate.opsForValue().get(key);
        if (codeFromRedis == null) {
            throw new IllegalConditionException("验证码不存在，请先获取验证码");
        }
        if (!codeFromRedis.equals(code)) {
            throw new IllegalConditionException("验证码不正确，请重新输入");
        }
        // 从 Redis 中移除验证码
        redisTemplate.delete(key);
    }

}
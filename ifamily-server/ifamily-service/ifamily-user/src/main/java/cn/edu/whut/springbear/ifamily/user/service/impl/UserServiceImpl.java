package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.api.ResultCodeEnum;
import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import cn.edu.whut.springbear.ifamily.common.constant.GlobalMessageConstants;
import cn.edu.whut.springbear.ifamily.common.constant.RedisConstants;
import cn.edu.whut.springbear.ifamily.common.constant.RegExpConstants;
import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.edu.whut.springbear.ifamily.common.exception.IncorrectConditionException;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.CommonUserVO;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.RoleUserVO;
import cn.edu.whut.springbear.ifamily.common.util.NicknameGenerator;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.user.constant.MessageConstants;
import cn.edu.whut.springbear.ifamily.user.mapper.UserMapper;
import cn.edu.whut.springbear.ifamily.user.pojo.po.UserDO;
import cn.edu.whut.springbear.ifamily.user.pojo.po.UsernameLogDO;
import cn.edu.whut.springbear.ifamily.user.pojo.query.LoginQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.query.ResetQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.UserVO;
import cn.edu.whut.springbear.ifamily.user.service.LoginLogService;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import cn.edu.whut.springbear.ifamily.user.service.UsernameLogService;
import cn.edu.whut.springbear.ifamily.user.service.feign.AuthFeignService;
import cn.edu.whut.springbear.ifamily.user.service.feign.UserRoleFeignService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
@AllArgsConstructor
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    private final UserRoleFeignService userRoleFeignService;
    private final RedisTemplate<String, String> redisTemplate;
    private final AuthFeignService authFeignService;
    private final LoginLogService loginLogService;
    private final HttpServletRequest httpServletRequest;
    private final UsernameLogService usernameLogService;

    @Override
    public UserDTO loadUserByUsername(String username) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserDO userDO = this.baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            return null;
        }

        // DO -> DTO
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO);

        // 查询用户对应的角色列表
        List<String> userRoleNames = this.userRoleFeignService.listRoleNamesOfUser(userDO.getId());
        userDTO.setRoles(userRoleNames);

        return userDTO;
    }

    @Override
    public CommonResult<Object> login(LoginQuery loginQuery) {
        // 根据用户名 || 手机 || 邮箱查询用户，验证用户信息是否存在
        String account = loginQuery.getAccount();
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", account).or().eq("email", account).or().eq("username", account);
        UserDO user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new IncorrectConditionException(MessageConstants.USER_NOT_EXISTS);
        }

        // 校验用户输入的密码是否正确
        if (!BCrypt.checkpw(loginQuery.getPassword(), user.getPassword())) {
            throw new IncorrectConditionException(MessageConstants.ERROR_PASSWORD);
        }

        // 请求认证服务器签发令牌
        Map<String, String> params = this.createTokenParams(user.getUsername(), loginQuery.getPassword());
        CommonResult<Object> tokenResponse = this.authFeignService.getAccessToken(params);

        // 签发令牌失败，直接返回失败结果
        if (!tokenResponse.getCode().equals(ResultCodeEnum.SUCCESS.getCode())) {
            return tokenResponse;
        }

        // 保存用户登录记录
        this.loginLogService.create(user.getId());
        // 更新上次登录时间
        UserDO userDO = new UserDO();
        userDO.setId(user.getId());
        userDO.setLastLogin(new Date());
        this.updateById(userDO);

        return tokenResponse;
    }

    @Override
    public CommonResult<Object> register(ResetQuery resetQuery) {
        UserDO userDO = new UserDO();
        String account = resetQuery.getAccount();

        // 正则表达式匹配客户端的注册方式是手机注册还是邮箱注册
        if (ReUtil.isMatch(RegExpConstants.PHONE_PATTERN, account)) {
            // 手机方式注册，验证手机号是否已被占用
            userDO.setPhone(account);
            this.verifyPhoneOrEmailExistence("phone", account);
        } else if (ReUtil.isMatch(RegExpConstants.EMAIL_PATTERN, account)) {
            // 邮箱方式注册，验证邮箱是否已被占用
            userDO.setEmail(account);
            this.verifyPhoneOrEmailExistence("email", account);
        } else {
            throw new IncorrectConditionException("账号类型不存在，请重新输入");
        }

        // 验证用户输入的验证码是否正确
        this.validateCode(account, resetQuery.getCode());

        // 设置用户注册所需的必要信息，ID 置空、用户名唯一、密码加密存储
        userDO.setId(null);
        userDO.setNickname(NicknameGenerator.generate());
        userDO.setUsername(IdUtil.simpleUUID());
        userDO.setPassword(BCrypt.hashpw(resetQuery.getPassword()));
        userDO.setDeleted(AssertEnum.NO.getCode());
        userDO.setStatus(AssertEnum.NO.getCode());
        Date date = new Date();
        userDO.setCreated(date);
        userDO.setModified(date);
        userDO.setLastLogin(date);

        // 注册用户，并保存用户登录记录
        this.save(userDO);
        this.loginLogService.create(userDO.getId());

        // 请求认证服务器签发令牌
        Map<String, String> tokenParams = this.createTokenParams(userDO.getUsername(), resetQuery.getPassword());
        return this.authFeignService.getAccessToken(tokenParams);
    }

    @Override
    public boolean reset(ResetQuery resetQuery) {
        // 根据手机号或邮箱查询用户信息是否存在
        String account = resetQuery.getAccount();
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", account).or().eq("email", account);
        UserDO user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new IncorrectConditionException(MessageConstants.USER_NOT_EXISTS);
        }

        // 判断新旧密码是否一致
        if (BCrypt.checkpw(resetQuery.getPassword(), user.getPassword())) {
            throw new IncorrectConditionException("新旧密码相同，请重新输入");
        }

        // 检验验证码是否正确
        this.validateCode(account, resetQuery.getCode());

        // 更新用户登录密码
        String encodedNewPassword = BCrypt.hashpw(resetQuery.getPassword());
        UserDO userDO = new UserDO();
        userDO.setId(user.getId());
        userDO.setPassword(encodedNewPassword);
        return this.updateById(userDO);
    }

    @Override
    public UserVO current() {
        // 从请求头中获取已通过认证的用户信息
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        // 查询用户详细信息
        UserDO userDO = this.getById(userDTO.getId());
        if (userDO == null) {
            throw new IncorrectConditionException(MessageConstants.USER_NOT_EXISTS);
        }

        // 手机号脱敏，隐藏中间四位为 *
        userDO.setPhone(DesensitizedUtil.mobilePhone(userDO.getPhone()));
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
    }

    @Override
    public boolean logout(Long userId, String password) {
        UserDO user = this.getById(userId);
        if (user == null) {
            throw new IncorrectConditionException(MessageConstants.USER_NOT_EXISTS);
        }

        // 验证输入的密码是否正确
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new IncorrectConditionException(MessageConstants.ERROR_PASSWORD);
        }

        return this.removeById(userId);
    }

    @Override
    public boolean updateSimpleProfile(Integer type, Long userId, String content) {
        UserDO userDO = new UserDO();
        userDO.setId(userId);

        final int wordsLimit = 30;
        // 类型：[1]用户昵称 [2]个性签名 [3]头像地址
        switch (type) {
            case 1:
                if (!StringUtils.hasLength(content) || content.length() > wordsLimit) {
                    throw new IncorrectConditionException("请填写用户昵称, 长度不大于 30");
                }
                userDO.setNickname(content);
                break;
            case 2:
                if (!StringUtils.hasLength(content) || content.length() > wordsLimit) {
                    throw new IncorrectConditionException("请填写个性签名, 长度不大于 30");
                }
                userDO.setSignature(content);
                break;
            case 3:
                if (!ReUtil.isMatch(RegExpConstants.URL_PATTERN, content)) {
                    throw new IncorrectConditionException("头像图片地址格式不正确");
                }
                userDO.setAvatar(content);
                break;
            default:
                throw new IncorrectConditionException("资料类型：[1]用户昵称 [2]个性签名 [3]头像地址");
        }

        return this.updateById(userDO);
    }

    @Override
    public boolean updateUsername(Long userId, String username, String password) {
        if (!ReUtil.isMatch(RegExpConstants.USERNAME_PATTERN, username)) {
            throw new IncorrectConditionException("用户名须以字母开头，可包含数字、字母、下划线和连字符，长度限 5-20 位");
        }

        UserDO user = this.getById(userId);
        if (user == null) {
            throw new IncorrectConditionException(MessageConstants.USER_NOT_EXISTS);
        }

        // 新用户名与旧用户名一致，不允许修改
        if (user.getUsername().equals(username)) {
            throw new IncorrectConditionException("新旧 UID 一致，请重新输入");
        }

        // 验证密码是否正确
        if (!BCrypt.checkpw(password, user.getPassword())) {
            throw new IncorrectConditionException(MessageConstants.ERROR_PASSWORD);
        }

        // 验证用户输入的用户名是否已被占用
        Object usernameObj = this.baseMapper.getCellValueByColumn("username", username);
        if (usernameObj != null) {
            throw new IncorrectConditionException("UID 已被占用，请重新输入");
        }

        // 查询当前用户用户名的上次更新时间，一年内只允许更新一次
        UsernameLogDO latest = this.usernameLogService.getLatest(user.getId());
        if (latest != null) {
            Date lastUpdate = latest.getCreated();
            long days = DateUtil.between(lastUpdate, new Date(), DateUnit.DAY);
            final int yearDays = 365;
            if (days < yearDays) {
                throw new IncorrectConditionException("UID 一年内只能修改一次，上次修改时间：" + DateUtil.formatDate(lastUpdate));
            }
        }

        // 保存用户最新的用户名修改记录
        boolean update = this.usernameLogService.create(user.getUsername(), username, user.getId());
        // 更新用户名
        UserDO userDO = new UserDO();
        userDO.setUsername(username);
        userDO.setId(user.getId());
        return update && this.updateById(userDO);
    }

    @Override
    public boolean updateEmail(Long userId, String email, String code) {
        if (!ReUtil.isMatch(RegExpConstants.EMAIL_PATTERN, email)) {
            throw new IncorrectConditionException(GlobalMessageConstants.INCORRECT_EMAIL_PATTERN);
        }

        UserDO user = this.getById(userId);
        if (user == null) {
            throw new IncorrectConditionException(MessageConstants.USER_NOT_EXISTS);
        }

        // 验证新旧邮箱是否一致，注意旧邮箱为 null
        if (user.getEmail() != null && email.equals(user.getEmail())) {
            throw new IncorrectConditionException("新旧邮箱一致，请重新输入");
        }

        // 验证邮箱是否已被占用
        this.verifyPhoneOrEmailExistence("email", email);
        // 验证用户输入的验证码是否正确
        this.validateCode(email, code);

        // 更新用户邮箱地址
        UserDO userDO = new UserDO();
        userDO.setEmail(email);
        userDO.setId(user.getId());
        return this.updateById(userDO);
    }

    @Override
    public boolean updatePhone(Long userId, String phone, String code) {
        if (!ReUtil.isMatch(RegExpConstants.PHONE_PATTERN, phone)) {
            throw new IncorrectConditionException(GlobalMessageConstants.INCORRECT_PHONE_PATTERN);
        }

        UserDO user = this.getById(userId);
        if (user == null) {
            throw new IncorrectConditionException(MessageConstants.USER_NOT_EXISTS);
        }

        // 验证新旧手机号是否一致，注意旧手机号为 null
        if (user.getPhone() != null && phone.equals(user.getPhone())) {
            throw new IncorrectConditionException("新旧手机号一致，请重新输入");
        }

        // 验证手机号是否已被占用
        this.verifyPhoneOrEmailExistence("phone", phone);
        // 验证用户输入的验证码是否正确
        this.validateCode(phone, code);

        // 更新用户手机号
        UserDO userDO = new UserDO();
        userDO.setPhone(phone);
        userDO.setId(user.getId());
        return this.updateById(userDO);
    }

    @Override
    public CommonUserVO getUserById(Long userId) {
        UserDO userDO = this.getById(userId);
        // DO -> DTO
        if (userDO != null) {
            CommonUserVO commonUserVO = new CommonUserVO();
            BeanUtils.copyProperties(userDO, commonUserVO);
            return commonUserVO;
        }
        return null;
    }

    @Override
    public List<RoleUserVO> listInBatchIds(List<Long> userIds) {
        if (CollUtil.isEmpty(userIds)) {
            return null;
        }

        List<UserDO> userDOList = this.listByIds(userIds);
        if (CollUtil.isEmpty(userDOList)) {
            return null;
        }

        // DO -> VO
        List<RoleUserVO> resultList = new ArrayList<>();
        for (UserDO userDO : userDOList) {
            RoleUserVO roleUserVO = new RoleUserVO();
            BeanUtils.copyProperties(userDO, roleUserVO);
            resultList.add(roleUserVO);
        }

        return resultList;
    }

    /**
     * 校验验证码正确性，将传入的验证码与 Redis 中存储的验证码进行比对
     *
     * @param account 需要验证的邮箱 | 手机
     * @param code    需要检验的验证码
     */
    private void validateCode(String account, String code) {
        String key = RedisConstants.CODE_PREFIX + account;
        String codeFromRedis = this.redisTemplate.opsForValue().get(key);
        if (codeFromRedis == null) {
            throw new IncorrectConditionException("验证码不存在，请先获取验证码");
        }
        if (!codeFromRedis.equals(code)) {
            throw new IncorrectConditionException("验证码不正确，请重新输入");
        }
        // 移除 Redis 中的验证码
        this.redisTemplate.delete(key);
    }

    /**
     * 验证手机号或邮箱的存在性
     *
     * @param column  email | phone
     * @param content 需要验证的内容
     */
    private void verifyPhoneOrEmailExistence(String column, String content) {
        Object cellValue = this.baseMapper.getCellValueByColumn(column, content);
        if (cellValue != null) {
            String chinese = "email".equals(column) ? "邮箱地址" : "手机号";
            throw new IncorrectConditionException(chinese + "已被占用，请重新输入");
        }
    }

    /**
     * 设置认证服务器签发令牌时所必须的参数
     *
     * @param username 用户名
     * @param password 密码
     */
    private Map<String, String> createTokenParams(String username, String password) {
        Map<String, String> params = new HashMap<>(6);
        params.put("grant_type", "password");
        params.put("client_id", AuthConstants.CLIENT_MOBILE_ID);
        params.put("client_secret", AuthConstants.COMMON_AUTH_PASSWORD);
        params.put("username", username);
        params.put("password", password);
        return params;
    }

}

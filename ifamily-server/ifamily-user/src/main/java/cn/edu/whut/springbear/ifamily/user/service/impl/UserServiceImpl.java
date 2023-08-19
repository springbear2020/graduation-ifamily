package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.common.enumerate.DeleteStatusEnum;
import cn.edu.whut.springbear.ifamily.common.enumerate.EnableStatusEnum;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.security.util.JwtUtils;
import cn.edu.whut.springbear.ifamily.user.constant.UserMessageConstants;
import cn.edu.whut.springbear.ifamily.user.mapper.UserMapper;
import cn.edu.whut.springbear.ifamily.user.pojo.dto.SecurityUserDetailsDTO;
import cn.edu.whut.springbear.ifamily.user.pojo.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.user.pojo.po.UserDO;
import cn.edu.whut.springbear.ifamily.user.pojo.po.UserLoginLogDO;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.vo.UserVO;
import cn.edu.whut.springbear.ifamily.user.service.PermissionService;
import cn.edu.whut.springbear.ifamily.user.service.UserLoginLogService;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    private HttpServletRequest request;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private UserLoginLogService userLoginLogService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserDO user = this.baseMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new UsernameNotFoundException(UserMessageConstants.USER_NOT_EXISTS);
        }

        // 查询当前用户下拥有的权限列表，提供给安全框架鉴权使用
        List<PermissionDO> permissions = this.permissionService.listPermissionsOfUser(user.getId());
        permissions = permissions == null ? new ArrayList<>() : permissions;
        return new SecurityUserDetailsDTO(user, permissions);
    }

    @Override
    public String login(String account, String password) {
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", account).or().eq("username", account).or().eq("email", account);
        UserDO user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new IllegalArgumentException(UserMessageConstants.USER_NOT_EXISTS);
        }

        // 验证密码正确性
        if (!this.passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalStateException("账号密码错误");
        }
        // 检查用户账号状态
        if (EnableStatusEnum.DISABLE.getCode().equals(user.getStatus())) {
            throw new IllegalStateException("禁止登录，用户状态异常");
        }

        // 查询当前用户的系统权限，将当前用户信息注入到安全框架中
        List<PermissionDO> permissions = this.permissionService.listPermissionsOfUser(user.getId());
        permissions = permissions == null ? new ArrayList<>() : permissions;
        SecurityUserDetailsDTO userDetails = new SecurityUserDetailsDTO(user, permissions);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 保存用户登录记录
        this.createUserLoginLog(user.getId());
        // 更新上次登录时间
        this.updateLastLogin(user.getId());

        return JwtUtils.create("username", user.getUsername());
    }

    @Override
    public Boolean create(UserQuery userQuery) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userQuery, userDO);
        // 新增用户时将 id 置空，由服务器生成
        userDO.setId(null);
        // 用户密码加密
        userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
        userDO.setCreated(new Date());
        userDO.setModified(new Date());
        userDO.setLastLogin(new Date());
        userDO.setStatus(EnableStatusEnum.ENABLE.getCode());
        userDO.setDeleted(DeleteStatusEnum.UNDELETED.getCode());
        return this.baseMapper.insert(userDO) == 1;
    }

    @Override
    public UserVO query(Long id) {
        UserDO userDO = this.baseMapper.selectById(id);
        if (userDO == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        return userVO;
    }

    @Override
    public Boolean remove(Long id) {
        return this.baseMapper.deleteById(id) == 1;
    }

    @Override
    public Boolean edit(UserQuery userQuery) {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userQuery, userDO);
        return this.baseMapper.updateById(userDO) == 1;
    }

    @Override
    public String verify(Integer type, String content) {
        // [1]验证用户名 [2]验证手机 [3]验证邮箱
        QueryWrapper<UserDO> queryWrapper = new QueryWrapper<>();
        switch (type) {
            case 1:
                queryWrapper.eq("username", content);
                break;
            case 2:
                queryWrapper.eq("phone", content);
                break;
            case 3:
                queryWrapper.eq("email", content);
                break;
            default:
                return null;
        }

        // 查询用户信息
        UserDO userDO = this.baseMapper.selectOne(queryWrapper);
        if (userDO == null) {
            return null;
        }

        return type == 1 ? userDO.getUsername() : (type == 2 ? userDO.getPhone() : userDO.getEmail());
    }

    /**
     * 保存用户登录记录
     */
    private void createUserLoginLog(Long userId) {
        UserLoginLogDO userLoginLogDO = new UserLoginLogDO();
        // 从请求头中解析 IP 地址
        String ipAddress = WebUtils.getRequestIp(request);
        userLoginLogDO.setIp(ipAddress);
        // 解析 IP 归属地，先使用百度地图 API，解析失败使用淘宝公共 API FIXME don't comment the next line after the development finished
        String location = WebUtils.baiduParseIpLocation(ipAddress);
        location = "未知地点".equals(location) ? WebUtils.taobaoParseIpLocation(ipAddress) : location;
        userLoginLogDO.setLocation(location);
        // 从请求头中获取用户设备信息
        String device = WebUtils.userAgent(request);
        userLoginLogDO.setDevice(device);
        Date date = new Date();
        userLoginLogDO.setLoginDatetime(date);
        userLoginLogDO.setCreated(date);
        userLoginLogDO.setModified(date);
        userLoginLogDO.setDeleted(DeleteStatusEnum.UNDELETED.getCode());
        userLoginLogDO.setUserId(userId);
        this.userLoginLogService.create(userLoginLogDO);
    }

    /**
     * 更新用户上次登录时间
     */
    private void updateLastLogin(Long userId) {
        UserDO userDO = new UserDO();
        userDO.setId(userId);
        userDO.setLastLogin(new Date());
        this.baseMapper.updateById(userDO);
    }

}
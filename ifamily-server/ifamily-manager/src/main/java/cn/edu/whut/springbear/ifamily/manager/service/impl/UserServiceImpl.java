package cn.edu.whut.springbear.ifamily.manager.service.impl;

import cn.edu.whut.springbear.ifamily.client.acl.AclFeignClient;
import cn.edu.whut.springbear.ifamily.client.user.UserFeignClient;
import cn.edu.whut.springbear.ifamily.common.constant.UserMessageConstants;
import cn.edu.whut.springbear.ifamily.common.enumerate.EnableStatusEnum;
import cn.edu.whut.springbear.ifamily.manager.service.UserService;
import cn.edu.whut.springbear.ifamily.model.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 17:48
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private AclFeignClient aclFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDO userDO = userFeignClient.getUserByUsername(username);
        if (userDO == null) {
            // [401]UsernameNotFoundException -> AuthenticationException -> RestfulUnauthorizedEntryPoint
            throw new UsernameNotFoundException(UserMessageConstants.USER_NOT_EXISTS);
        }

        // 检查用户账号状态
        if (EnableStatusEnum.DISABLE.getCode().equals(userDO.getStatus())) {
            // [401] RestfulUnauthorizedEntryPoint
            throw new UsernameNotFoundException(UserMessageConstants.ILLEGAL_USER_STATUS);
        }

        // 查询用户拥有的所有系统权限，提供给安全框架鉴权使用
        List<PermissionDO> permissions = this.aclFeignClient.listPermissionsOfUser(userDO.getId());
        List<PermissionDO> finalPermissions = permissions == null ? new ArrayList<>() : permissions;

        return new UserDetails() {

            private static final long serialVersionUID = 7183169499366072253L;

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return finalPermissions.stream()
                        .map(permission -> new SimpleGrantedAuthority(permission.getId() + ":" + permission.getName()))
                        .collect(Collectors.toList());
            }

            @Override
            public String getPassword() {
                return userDO.getPassword();
            }

            @Override
            public String getUsername() {
                return userDO.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return EnableStatusEnum.ENABLE.getCode().equals(userDO.getStatus());
            }
        };
    }

}

package cn.edu.whut.springbear.ifamily.user.config;

import cn.edu.whut.springbear.ifamily.client.acl.AclFeignClient;
import cn.edu.whut.springbear.ifamily.common.constant.UserMessageConstants;
import cn.edu.whut.springbear.ifamily.model.dto.SecurityUserDetailsDTO;
import cn.edu.whut.springbear.ifamily.model.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import cn.edu.whut.springbear.ifamily.security.access.DynamicPermissionProvider;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Spring-_-Bear
 * @since 23/03/20 08:32
 */
@Configuration
public class UserSecurityConfig {

    @Autowired
    private UserService userService;
    @Autowired
    private AclFeignClient aclFeignClient;

    /**
     * 提供给安全框架鉴权使用
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UserDO user = this.userService.getUserByUsername(username);
            if (user == null) {
                // [401]UsernameNotFoundException -> AuthenticationException -> RestfulUnauthorizedEntryPoint
                throw new UsernameNotFoundException(UserMessageConstants.USER_NOT_EXISTS);
            }

            // 检查用户账号状态
            if (SecurityUserDetailsDTO.EnableStatusEnum.DISABLE.getCode().equals(user.getStatus())) {
                // [401] UsernameNotFoundException -> AuthenticationException -> RestfulUnauthorizedEntryPoint
                throw new UsernameNotFoundException(UserMessageConstants.ILLEGAL_USER_STATUS);
            }

            // 查询当前用户下拥有的权限列表，提供给安全框架鉴权使用
            List<PermissionDO> permissions = aclFeignClient.listPermissionsOfUser(user.getId());
            permissions = permissions == null ? new ArrayList<>() : permissions;
            return new SecurityUserDetailsDTO(user, permissions);
        };
    }

    /**
     * 动态配置系统权限数据源
     */
    @Bean
    public DynamicPermissionProvider dynamicPermissionProvider() {
        return () -> {
            List<PermissionDO> permissionsList = this.aclFeignClient.listAllPermissions();
            permissionsList = permissionsList == null ? new ArrayList<>() : permissionsList;
            Map<String, ConfigAttribute> map = new ConcurrentHashMap<>(permissionsList.size());
            permissionsList.forEach(p -> map.put(p.getPath(), new org.springframework.security.access.SecurityConfig(p.getId() + ":" + p.getName())));
            return map;
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

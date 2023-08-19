package cn.edu.whut.springbear.ifamily.genealogy.config;

import cn.edu.whut.springbear.ifamily.client.acl.AclFeignClient;
import cn.edu.whut.springbear.ifamily.client.user.UserFeignClient;
import cn.edu.whut.springbear.ifamily.common.constant.UserMessageConstants;
import cn.edu.whut.springbear.ifamily.common.enumerate.EnableStatusEnum;
import cn.edu.whut.springbear.ifamily.model.dto.SecurityUserDetailsDTO;
import cn.edu.whut.springbear.ifamily.model.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 16:09
 */
@Configuration
public class GenealogySecurityConfig {

    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private AclFeignClient aclFeignClient;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            UserDO user = userFeignClient.getUserByUsername(username);
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
            List<PermissionDO> permissions = aclFeignClient.listPermissionsOfUser(user.getId());
            permissions = permissions == null ? new ArrayList<>() : permissions;
            return new SecurityUserDetailsDTO(user, permissions);
        };
    }

}

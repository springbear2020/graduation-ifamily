package cn.edu.whut.springbear.ifamily.security.access;

import cn.hutool.core.collection.CollUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 动态权限决策管理器
 *
 * @author Spring-_-Bear
 * @since 23/03/19 10:23
 */
@Component
@ConditionalOnBean(name = "dynamicPermissionProvider")
public class DynamicAccessDecisionManager implements AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 当前用户请求无需权限控制，直接放行
        if (CollUtil.isEmpty(configAttributes)) {
            return;
        }

        Collection<? extends GrantedAuthority> grantedAuthorities = authentication.getAuthorities();
        // 检查当前登录用户已授权的权限 grantedAuthorities 中是否包含当前请求所需权限 configAttributes
        for (ConfigAttribute configAttribute : configAttributes) {
            String needAuthority = configAttribute.getAttribute();
            for (GrantedAuthority grantedAuthority : grantedAuthorities) {
                if (needAuthority.trim().equals(grantedAuthority.getAuthority())) {
                    return;
                }
            }
        }

        // [403]org.springframework.security.access.AccessDeniedException -> cn.edu.whut.springbear.ifamily.security.access.RestfulForbiddenHandler
        throw new AccessDeniedException("抱歉，您缺少动态访问权限");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}

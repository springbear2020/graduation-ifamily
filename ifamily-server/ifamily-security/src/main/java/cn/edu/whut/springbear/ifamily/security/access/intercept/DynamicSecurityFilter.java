package cn.edu.whut.springbear.ifamily.security.access.intercept;

import cn.edu.whut.springbear.ifamily.security.access.DynamicAccessDecisionManager;
import cn.edu.whut.springbear.ifamily.security.access.DynamicPermissionMetadataSource;
import cn.edu.whut.springbear.ifamily.security.config.WhitelistResourcePathConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 动态权限过滤器
 *
 * @author Spring-_-Bear
 * @since 23/03/19 11:29
 */
@Component
@ConditionalOnBean(name = "dynamicPermissionProvider")
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private DynamicPermissionMetadataSource dynamicPermissionMetadataSource;
    @Autowired
    private WhitelistResourcePathConfig whitelistResourcePathConfig;

    @Autowired
    public void setAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);

        // OPTIONS 请求直接放行
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
            return;
        }

        // 白名单请求直接放行
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String path : this.whitelistResourcePathConfig.getUrls()) {
            if (pathMatcher.match(path, request.getRequestURI())) {
                fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
                return;
            }
        }

        // 此处会调用 AccessDecisionManager 中的 decide 方法进行鉴权操作
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.dynamicPermissionMetadataSource;
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

}

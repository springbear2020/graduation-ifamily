package cn.edu.whut.springbear.ifamily.security.access;

import cn.hutool.core.util.URLUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 动态权限数据源
 *
 * @author Spring-_-Bear
 * @since 23/03/19 10:41
 */
@Component
@ConditionalOnBean(name = "dynamicPermissionProvider")
public class DynamicPermissionMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static Map<String, ConfigAttribute> configAttributeMap;

    @Autowired
    private DynamicPermissionProvider dynamicPermissionProvider;

    @PostConstruct
    public void loadDataSource() {
        configAttributeMap = this.dynamicPermissionProvider.loadDataSource();
    }

    /**
     * 动态配置访问当前请求所需的权限数据源
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (configAttributeMap == null) {
            this.loadDataSource();
        }

        List<ConfigAttribute> configAttributes = new ArrayList<>();

        // 获取当前访问路径
        String url = ((FilterInvocation) o).getRequestUrl();
        String requestPath = URLUtil.getPath(url);

        PathMatcher pathMatcher = new AntPathMatcher();
        Set<String> configAttributeSet = configAttributeMap.keySet();
        for (String path : configAttributeSet) {
            if (pathMatcher.match(path, requestPath)) {
                // 将系统权限列表中匹配当前请求的资源路径配置到当前请求所需权限列表中 configAttributes
                configAttributes.add(configAttributeMap.get(path));
            }
        }
        return configAttributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        if (configAttributeMap == null) {
            this.loadDataSource();
        }

        List<ConfigAttribute> configAttributes = new ArrayList<>();
        configAttributeMap.keySet().forEach(path -> configAttributes.add(configAttributeMap.get(path)));

        return configAttributes;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}

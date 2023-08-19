package cn.edu.whut.springbear.ifamily.security.access;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 10:41
 */
@FunctionalInterface
public interface DynamicPermissionProvider {
    /**
     * 加载资源 Ant 通配符和资源对应 Map
     *
     * @return Ant 通配符和资源对应 Map
     */
    Map<String, ConfigAttribute> loadDataSource();
}

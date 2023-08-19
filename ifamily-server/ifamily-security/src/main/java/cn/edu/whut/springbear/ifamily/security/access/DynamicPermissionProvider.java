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
     * 加载系统动态权限数据源
     *
     * @return key = permission.getPath(); val = permission.getId() + ":" + permission.getName();
     */
    Map<String, ConfigAttribute> loadDataSource();

}

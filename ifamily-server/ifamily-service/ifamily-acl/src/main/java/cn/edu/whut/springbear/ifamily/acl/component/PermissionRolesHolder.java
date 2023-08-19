package cn.edu.whut.springbear.ifamily.acl.component;

import cn.edu.whut.springbear.ifamily.acl.service.RolePermissionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author Spring-_-Bear
 * @since 23/04/13 10:40
 */
@AllArgsConstructor
@Component
public class PermissionRolesHolder {

    private final RolePermissionService rolePermissionService;

    /**
     * 初始化权限路径 path 与角色名称集合 Map，并存入 Redis 以提供给 ifamily-gateway 鉴权使用，
     * 其中 Map 的键 key 为权限路径，值 val 为权限路径对应的一系列角色名称集合
     */
    @PostConstruct
    public void initPermissionRoleNamesMap() {
        this.rolePermissionService.initPermissionRoleNamesMap();
    }

}

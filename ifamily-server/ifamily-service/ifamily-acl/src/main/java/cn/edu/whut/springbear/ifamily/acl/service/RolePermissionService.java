package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.RolePermissionDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface RolePermissionService extends IService<RolePermissionDO> {

    /**
     * 初始化权限路径 path 与角色名称集合 Map，并存入 Redis 以提供给 ifamily-gateway 鉴权使用
     *
     * @return [key]权限路径 [val]权限路径对应的一系列角色名称集合
     */
    Map<String, List<String>> initPermissionRoleNamesMap();

}

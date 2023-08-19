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
     * 批量保存角色与权限关系
     *
     * @param roleId        角色 ID
     * @param permissionIds 权限 ID 集合
     */
    void saveInBatchIds(Long roleId, List<Long> permissionIds);

    /**
     * 查询角色对应的所有权限 ID 列表
     *
     * @param roleId 角色 ID
     * @return 权限 ID 列表
     */
    List<Long> listPermissionIdsByRoleId(Long roleId);

    /**
     * 删除角色对应的权限关系
     *
     * @param roleId 角色 ID
     */
    void removeByRoleId(Long roleId);

    /**
     * 查询所有的角色 - 权限关系，将结果根据权限 ID 进行分组，从而得到每个权限 ID 对应的一系列角色 ID 集合
     *
     * @return [key]权限 ID [val]权限 ID 对应的一系列角色 ID 集合
     */
    Map<Long, List<Long>> getPermissionRoleIdsGroupMap();

}

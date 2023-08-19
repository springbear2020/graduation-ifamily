package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.RoleMenuDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface RoleMenuService extends IService<RoleMenuDO> {

    /**
     * 批量保存角色与菜单关系
     *
     * @param roleId  角色 ID
     * @param menuIds 菜单 ID 列表
     */
    void saveInBatchIds(Long roleId, List<Long> menuIds);

    /**
     * 查询角色对应的所有菜单 ID 列表
     *
     * @param roleId 角色 ID
     * @return 菜单 ID 列表
     */
    List<Long> listMenuIdsByRoleId(Long roleId);

    /**
     * 删除角色对应的菜单关系
     *
     * @param roleId 角色 ID
     */
    void removeByRoleId(Long roleId);

    /**
     * 查询角色列表对应的菜单 ID 列表
     *
     * @param roleIds 角色 ID 集合
     * @return 去重后的菜单 ID 集合
     */
    Set<Long> listMenuIdsInRoleIds(List<Long> roleIds);

}

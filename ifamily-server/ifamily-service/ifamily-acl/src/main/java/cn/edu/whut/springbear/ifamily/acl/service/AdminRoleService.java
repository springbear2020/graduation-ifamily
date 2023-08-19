package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.AdminRoleDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface AdminRoleService extends IService<AdminRoleDO> {

    /**
     * 查询管理员对应的角色 ID 集合
     *
     * @param adminId 管理员 ID
     * @return 角色 ID 集合
     */
    List<Long> listRoleIdsOfAdmin(Long adminId);

    /**
     * 删除角色对应的所有管理员关系
     *
     * @param roleId 角色 ID
     */
    void removeByRoleId(Long roleId);

    /**
     * 查询角色对应的管理员 ID 集合
     *
     * @param roleId 角色 ID
     * @return 管理员 ID 集合
     */
    List<Long> listAdminIdsOfRole(Long roleId);

}

package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.UserRoleDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface UserRoleService extends IService<UserRoleDO> {

    /**
     * 删除角色对应的所有用户关系
     *
     * @param roleId 角色 ID
     */
    void removeByRoleId(Long roleId);

    /**
     * 查询用户对应的角色 ID 集合
     *
     * @param userId 用户 ID
     * @return 角色 ID 集合
     */
    List<Long> listRoleIdsOfUser(Long userId);

    /**
     * 查询角色对应的用户 ID 集合
     *
     * @param roleId 角色 ID
     * @return 用户 ID 集合
     */
    List<Long> listUserIdsOfRole(Long roleId);

}

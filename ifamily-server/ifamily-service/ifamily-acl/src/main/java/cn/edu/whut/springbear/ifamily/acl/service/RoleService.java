package cn.edu.whut.springbear.ifamily.acl.service;

import cn.edu.whut.springbear.ifamily.acl.pojo.bo.RoleBO;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.RoleDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.query.RoleQuery;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
public interface RoleService extends IService<RoleDO> {

    /**
     * 初始化权限路径 path 与角色名称集合 Map，并存入 Redis 以提供给 ifamily-gateway 鉴权使用
     */
    void initPermissionRoleNamesMap();

    /**
     * 查询用户拥有的所有角色名称集合
     *
     * @param userId 用户 ID
     * @return 角色名称集合
     */
    List<String> listRoleNamesOfUser(Long userId);

    /**
     * 查询管理员拥有的所有角色名称集合
     *
     * @param adminId 管理员 ID
     * @return 角色名称集合
     */
    List<String> listRoleNamesOfAdmin(Long adminId);

    /**
     * 保存系统角色
     *
     * @param roleQuery 角色信息
     * @return [true]保存成功
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean create(RoleQuery roleQuery);

    /**
     * 查询所有角色，包含角色对应的权限、菜单列表详细信息
     *
     * @return 角色详细信息
     */
    List<RoleBO> listWithDetails();

    /**
     * 删除系统角色，同时删除角色所对应的一系列关系
     *
     * @param roleId 角色 ID
     * @return [true]删除成功
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean removeRole(Long roleId);

    /**
     * 更新系统角色，包括更新角色对应的菜单、权限关系信息
     *
     * @param roleQuery 新角色信息
     * @return [true]更新成功
     */
    @Transactional(rollbackFor = RuntimeException.class)
    boolean edit(RoleQuery roleQuery);

}

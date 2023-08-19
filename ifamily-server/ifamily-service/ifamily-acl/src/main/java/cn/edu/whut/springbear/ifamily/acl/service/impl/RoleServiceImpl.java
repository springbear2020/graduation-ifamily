package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.RoleMapper;
import cn.edu.whut.springbear.ifamily.acl.pojo.bo.RoleBO;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.RoleDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.query.RoleQuery;
import cn.edu.whut.springbear.ifamily.acl.service.*;
import cn.edu.whut.springbear.ifamily.acl.service.feign.AdminFeignService;
import cn.edu.whut.springbear.ifamily.acl.service.feign.UserFeignService;
import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
@RequiredArgsConstructor
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {

    private final UserRoleService userRoleService;
    private final AdminRoleService adminRoleService;
    private final RoleMenuService roleMenuService;
    private final PermissionService permissionService;
    private final RolePermissionService rolePermissionService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AdminFeignService adminFeignService;
    private final UserFeignService userFeignService;

    @Override
    public void initPermissionRoleNamesMap() {
        // 删除缓存中已有的 <权限路径, 权限对应的所有角色名称集合> 数据
        this.redisTemplate.delete(AuthConstants.PERMISSION_ROLES_MAP_KEY);

        // 查询 <权限 ID，权限 ID 对应的一系列角色 ID> Map 集合
        Map<Long, List<Long>> permissionRoleIdsMap = this.rolePermissionService.getPermissionRoleIdsGroupMap();
        if (CollUtil.isEmpty(permissionRoleIdsMap)) {
            return;
        }
        Set<Map.Entry<Long, List<Long>>> permissionIdRoleIdsEntries = permissionRoleIdsMap.entrySet();

        // 遍历 permissionIdRoleIdsEntries，根据键（权限 ID）查询权限，根据值（角色 ID 集合）查询权限对应的所有角色名称
        Map<String, List<String>> permissionRoleNamesMap = new HashMap<>(permissionRoleIdsMap.size());
        for (Map.Entry<Long, List<Long>> entry : permissionIdRoleIdsEntries) {
            Long permissionId = entry.getKey();
            PermissionDO permissionDO = this.permissionService.getByStatusAndId(permissionId, AssertEnum.NO.getCode());

            if (permissionDO != null) {
                // 根据 permissionRoleIds 批量查询未禁用的角色名称集合
                List<Long> permissionRoleIds = entry.getValue();
                List<String> roleNames = this.listRoleNamesInBatchIds(new ArrayList<>(permissionRoleIds));

                // 将 <权限路径, 权限对应的所有角色名称集合> 结果添加至 permissionRoleNamesMap 中
                permissionRoleNamesMap.put(permissionDO.getPath(), roleNames);
            }
        }

        this.redisTemplate.opsForHash().putAll(AuthConstants.PERMISSION_ROLES_MAP_KEY, permissionRoleNamesMap);
    }

    @Override
    public List<String> listRoleNamesOfUser(Long userId) {
        List<Long> userRoleIds = this.userRoleService.listRoleIdsOfUser(userId);
        if (CollUtil.isEmpty(userRoleIds)) {
            return null;
        }

        // 查询用户对应的所有未禁用角色名称集合返回
        return this.listRoleNamesInBatchIds(userRoleIds);
    }

    @Override
    public List<String> listRoleNamesOfAdmin(Long adminId) {
        List<Long> adminRoleIds = this.adminRoleService.listRoleIdsOfAdmin(adminId);
        if (CollUtil.isEmpty(adminRoleIds)) {
            return null;
        }

        // 查询管理员对应的所有未禁用角色名称集合返回
        return this.listRoleNamesInBatchIds(adminRoleIds);

    }

    @Override
    public boolean create(RoleQuery roleQuery) {
        RoleDO roleDO = new RoleDO();
        BeanUtils.copyProperties(roleQuery, roleDO);
        roleDO.setId(null);
        Date date = new Date();
        roleDO.setCreated(date);
        roleDO.setModified(date);
        boolean createResult = this.save(roleDO);

        // 保存角色对应的菜单资源和权限资源关系
        roleQuery.setId(roleDO.getId());
        this.saveRoleSystemResources(roleQuery);

        // 及时更新 <权限路径, 权限对应的所有角色名称集合> 缓存信息
        this.initPermissionRoleNamesMap();

        return createResult;
    }

    @Override
    public List<RoleBO> listWithDetails() {
        List<RoleDO> roleDOList = this.list();
        if (CollUtil.isEmpty(roleDOList)) {
            return null;
        }

        List<RoleBO> resultList = new ArrayList<>();
        for (RoleDO roleDO : roleDOList) {
            RoleBO roleBO = new RoleBO();
            roleBO.setRole(roleDO);
            final long roleId = roleDO.getId();

            // 查询当前角色对应的菜单 ID 列表
            roleBO.setMenuIds(this.roleMenuService.listMenuIdsByRoleId(roleId));
            // 查询当前角色对应的权限 ID 列表
            roleBO.setPermissionIds(this.rolePermissionService.listPermissionIdsByRoleId(roleId));
            // 查询当前角色对应的用户 ID 列表
            List<Long> roleUserIds = this.userRoleService.listUserIdsOfRole(roleId);
            if (CollUtil.isNotEmpty(roleUserIds)) {
                // 服务调用批量查询用户信息
                roleBO.setUsers(this.userFeignService.listInBatchIds(roleUserIds));
            }
            // 查询当前角色对应的管理员 ID 列表
            List<Long> roleAdminIds = this.adminRoleService.listAdminIdsOfRole(roleId);
            if (CollUtil.isNotEmpty(roleAdminIds)) {
                // 服务调用批量查询管理员信息
                roleBO.setAdmins(this.adminFeignService.listInBatchIds(roleAdminIds));
            }

            resultList.add(roleBO);
        }

        return resultList;
    }

    @Override
    public boolean removeRole(Long roleId) {
        // 删除角色对应的所有权限资源
        this.rolePermissionService.removeByRoleId(roleId);
        // 删除角色对应的所有菜单权限
        this.roleMenuService.removeByRoleId(roleId);
        // 删除用户对应的当前角色
        this.userRoleService.removeByRoleId(roleId);
        // 删除管理员对应的当前角色
        this.adminRoleService.removeByRoleId(roleId);

        // 及时更新 <权限路径, 权限对应的所有角色名称集合> 缓存信息
        this.initPermissionRoleNamesMap();

        return this.removeById(roleId);
    }

    @Override
    public boolean edit(RoleQuery roleQuery) {
        // 删除角色对应的所有权限资源
        this.rolePermissionService.removeByRoleId(roleQuery.getId());
        // 删除角色对应的所有菜单权限
        this.roleMenuService.removeByRoleId(roleQuery.getId());

        // 保存角色对应的菜单资源和权限资源关系
        this.saveRoleSystemResources(roleQuery);

        // 及时更新 <权限路径, 权限对应的所有角色名称集合> 缓存信息
        this.initPermissionRoleNamesMap();

        RoleDO roleDO = new RoleDO();
        BeanUtils.copyProperties(roleQuery, roleDO);

        return this.updateById(roleDO);
    }

    /**
     * 批量查询未禁用角色名称返回
     *
     * @param roleIds 角色 ID 集合
     * @return 角色对应的名称集合
     */
    private List<String> listRoleNamesInBatchIds(List<Long> roleIds) {
        QueryWrapper<RoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", AssertEnum.NO.getCode()).in("id", roleIds);
        List<RoleDO> roles = this.list(queryWrapper);
        if (CollUtil.isEmpty(roles)) {
            return null;
        }

        return roles.stream()
                .map(RoleDO::getName)
                .collect(Collectors.toList());
    }

    /**
     * 保存角色对应的菜单资源和权限资源关系
     *
     * @param roleQuery 角色信息，包含当前角色所关联的菜单和权限 ID 集合
     */
    public void saveRoleSystemResources(RoleQuery roleQuery) {
        // 保存角色对应的权限 ID
        List<Long> permissionIds = roleQuery.getPermissionIds();
        if (CollUtil.isNotEmpty(permissionIds)) {
            this.rolePermissionService.saveInBatchIds(roleQuery.getId(), permissionIds);
        }

        // 保存角色对应的菜单 ID
        List<Long> menuIds = roleQuery.getMenuIds();
        if (CollUtil.isNotEmpty(menuIds)) {
            this.roleMenuService.saveInBatchIds(roleQuery.getId(), menuIds);
        }
    }

}

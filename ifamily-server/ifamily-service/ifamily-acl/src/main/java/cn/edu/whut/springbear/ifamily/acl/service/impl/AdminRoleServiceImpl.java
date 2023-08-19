package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.AdminRoleMapper;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.AdminRoleDO;
import cn.edu.whut.springbear.ifamily.acl.service.AdminRoleService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
@AllArgsConstructor
@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRoleDO> implements AdminRoleService {

    @Override
    public List<Long> listRoleIdsOfAdmin(Long userId) {
        // 查询用户对应的所有角色关系
        QueryWrapper<AdminRoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("admin_id", userId);
        List<AdminRoleDO> userRoleRelations = this.list(queryWrapper);
        if (CollUtil.isEmpty(userRoleRelations)) {
            return null;
        }

        // 过滤出用户对应的角色 ID 集合
        return userRoleRelations.stream()
                .map(AdminRoleDO::getRoleId)
                .collect(Collectors.toList());
    }

    @Override
    public void removeByRoleId(Long roleId) {
        UpdateWrapper<AdminRoleDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_id", roleId);
        this.remove(updateWrapper);
    }

    @Override
    public List<Long> listAdminIdsOfRole(Long roleId) {
        // 查询角色对应的所有管理员关系
        QueryWrapper<AdminRoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        List<AdminRoleDO> adminRoleRelations = this.list(queryWrapper);
        if (CollUtil.isEmpty(adminRoleRelations)) {
            return null;
        }

        // 过滤出角色对应的管理员 ID 集合
        return adminRoleRelations.stream()
                .map(AdminRoleDO::getAdminId)
                .collect(Collectors.toList());
    }

    @Override
    public boolean addAdminRole(Long adminId, Long roleId) {
        AdminRoleDO adminRoleDO = new AdminRoleDO();
        adminRoleDO.setAdminId(adminId);
        adminRoleDO.setRoleId(roleId);
        return this.save(adminRoleDO);
    }

    @Override
    public boolean removeAdminRole(Long adminId, Long roleId) {
        UpdateWrapper<AdminRoleDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("admin_id", adminId).eq("role_id", roleId);
        return this.remove(updateWrapper);
    }

}

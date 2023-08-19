package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.RoleMenuMapper;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.RoleMenuDO;
import cn.edu.whut.springbear.ifamily.acl.service.RoleMenuService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenuDO> implements RoleMenuService {

    @Override
    public void saveInBatchIds(Long roleId, List<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return;
        }

        List<RoleMenuDO> entityList = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenuDO roleMenuDO = new RoleMenuDO();
            roleMenuDO.setRoleId(roleId);
            roleMenuDO.setMenuId(menuId);
            entityList.add(roleMenuDO);
        }

        this.saveBatch(entityList);
    }

    @Override
    public List<Long> listMenuIdsByRoleId(Long roleId) {
        QueryWrapper<RoleMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        List<RoleMenuDO> roleMenuDOList = this.list(queryWrapper);
        if (CollUtil.isEmpty(roleMenuDOList)) {
            return null;
        }

        return roleMenuDOList.stream()
                .map(RoleMenuDO::getMenuId)
                .collect(Collectors.toList());
    }

    @Override
    public void removeByRoleId(Long roleId) {
        UpdateWrapper<RoleMenuDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_id", roleId);
        this.remove(updateWrapper);
    }

    @Override
    public Set<Long> listMenuIdsInRoleIds(List<Long> roleIds) {
        QueryWrapper<RoleMenuDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("role_id", roleIds);
        List<RoleMenuDO> roleMenuRelations = this.list(queryWrapper);
        if (roleMenuRelations.isEmpty()) {
            return null;
        }

        return roleMenuRelations.stream()
                .map(RoleMenuDO::getMenuId)
                .collect(Collectors.toSet());
    }

}

package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.RolePermissionMapper;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.RolePermissionDO;
import cn.edu.whut.springbear.ifamily.acl.service.RolePermissionService;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
@AllArgsConstructor
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionDO> implements RolePermissionService {

    @Override
    public void saveInBatchIds(Long roleId, List<Long> permissionIds) {
        if (CollUtil.isEmpty(permissionIds)) {
            return;
        }

        List<RolePermissionDO> entityList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            RolePermissionDO rolePermissionDO = new RolePermissionDO();
            rolePermissionDO.setRoleId(roleId);
            rolePermissionDO.setPermissionId(permissionId);
            entityList.add(rolePermissionDO);
        }

        this.saveBatch(entityList);
    }

    @Override
    public List<Long> listPermissionIdsByRoleId(Long roleId) {
        QueryWrapper<RolePermissionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        List<RolePermissionDO> rolePermissionDOList = this.list(queryWrapper);
        if (CollUtil.isEmpty(rolePermissionDOList)) {
            return null;
        }

        return rolePermissionDOList.stream()
                .map(RolePermissionDO::getPermissionId)
                .collect(Collectors.toList());
    }

    @Override
    public void removeByRoleId(Long roleId) {
        UpdateWrapper<RolePermissionDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_id", roleId);
        this.remove(updateWrapper);
    }

    @Override
    public Map<Long, List<Long>> getPermissionRoleIdsGroupMap() {
        List<RolePermissionDO> rolePermissionRelations = this.list();
        if (CollUtil.isEmpty(rolePermissionRelations)) {
            return null;
        }

        return rolePermissionRelations.stream()
                .collect(Collectors.groupingBy(
                        RolePermissionDO::getPermissionId,
                        Collectors.mapping(RolePermissionDO::getRoleId, Collectors.toList())
                ));
    }

}

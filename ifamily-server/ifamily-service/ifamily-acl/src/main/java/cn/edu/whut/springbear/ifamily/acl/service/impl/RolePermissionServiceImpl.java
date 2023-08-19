package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.RolePermissionMapper;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.RoleDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.RolePermissionDO;
import cn.edu.whut.springbear.ifamily.acl.service.PermissionService;
import cn.edu.whut.springbear.ifamily.acl.service.RolePermissionService;
import cn.edu.whut.springbear.ifamily.acl.service.RoleService;
import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
@AllArgsConstructor
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionDO> implements RolePermissionService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final PermissionService permissionService;
    private final RoleService roleService;

    @Override
    public Map<String, List<String>> initPermissionRoleNamesMap() {
        // 从角色 - 权限关系表中查询出所有的对应关系
        List<RolePermissionDO> rolePermissionRelations = this.baseMapper.selectList(new QueryWrapper<>());
        if (CollUtil.isEmpty(rolePermissionRelations)) {
            return null;
        }

        // 将角色 - 权限关系按照权限 ID 进行分组，从而得到每个权限下对应的一系列角色
        Map<Long, List<RolePermissionDO>> permissionRolesMap = rolePermissionRelations.stream()
                .collect(Collectors.groupingBy(RolePermissionDO::getPermissionId));
        Set<Map.Entry<Long, List<RolePermissionDO>>> entries = permissionRolesMap.entrySet();
        Map<String, List<String>> resultMap = new HashMap<>(entries.size());
        // 遍历 permissionRolesMap，根据键查询权限，根据值集合查询权限对应的所有角色名称
        for (Map.Entry<Long, List<RolePermissionDO>> entry : entries) {
            Long permissionId = entry.getKey();
            PermissionDO permissionDO = this.permissionService.getByStatusAndId(permissionId, AssertEnum.NO.getCode());
            if (permissionDO != null) {
                Set<Long> permissionRoleIds = entry.getValue().stream()
                        .map(RolePermissionDO::getRoleId)
                        .collect(Collectors.toSet());
                // 查询权限对应的所有未禁用角色
                List<RoleDO> permissionRoles = this.roleService.listByStatusInBatchIds(new ArrayList<>(permissionRoleIds), AssertEnum.NO.getCode());
                if (CollUtil.isEmpty(permissionRoles)) {
                    continue;
                }

                // 过滤出角色名称集合，并将结果添加至 resultMap 中
                List<String> permissionRoleNames = permissionRoles.stream()
                        .map(RoleDO::getName)
                        .collect(Collectors.toList());
                resultMap.put(permissionDO.getPath(), permissionRoleNames);
            }
        }

        this.redisTemplate.delete(AuthConstants.PERMISSION_ROLES_MAP_KEY);
        this.redisTemplate.opsForHash().putAll(AuthConstants.PERMISSION_ROLES_MAP_KEY, resultMap);
        return resultMap;
    }

}

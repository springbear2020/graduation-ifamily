package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.user.mapper.PermissionMapper;
import cn.edu.whut.springbear.ifamily.user.pojo.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.user.service.PermissionService;
import cn.edu.whut.springbear.ifamily.user.service.RolePermissionService;
import cn.edu.whut.springbear.ifamily.user.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionDO> implements PermissionService {

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public List<PermissionDO> listPermissionsOfUser(Long userId) {
        // 查询用户对应的所有角色 ID 集合，注意去重
        List<Long> roleIds = this.userRoleService.listRoleIdsOfUser(userId);
        if (roleIds.isEmpty()) {
            return null;
        }

        // 查询角色列表下对应的权限 ID 集合，注意去重
        List<Long> permissionIds = this.rolePermissionService.listPermissionIdsOfRoles(roleIds);
        if (permissionIds == null) {
            return null;
        }

        // 根据权限 ID 集合查询所有权限，真正实现查询用户拥有的所有权限
        QueryWrapper<PermissionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", permissionIds);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<PermissionDO> listAll() {
        return this.baseMapper.selectList(new QueryWrapper<>());
    }

}

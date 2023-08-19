package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.AdminRoleMapper;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.AdminRoleDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.RoleDO;
import cn.edu.whut.springbear.ifamily.acl.service.AdminRoleService;
import cn.edu.whut.springbear.ifamily.acl.service.RoleService;
import cn.edu.whut.springbear.ifamily.common.enumerate.AssertEnum;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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

    private final RoleService roleService;

    @Override
    public List<String> listRoleNamesOfAdmin(Long adminId) {
        // 查询出管理员对应的所有角色 ID，注意去重
        QueryWrapper<AdminRoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct role_id").eq("admin_id", adminId);
        List<AdminRoleDO> adminRoleRelations = this.baseMapper.selectList(queryWrapper);
        if (adminRoleRelations == null || adminRoleRelations.isEmpty()) {
            return null;
        }

        // 过滤出管理员对应的角色 ID 集合
        List<Long> adminRoleIds = adminRoleRelations.stream()
                .map(AdminRoleDO::getRoleId)
                .collect(Collectors.toList());
        // 查询出管理员对应的所有未禁用角色，并过滤出角色名称集合返回
        List<RoleDO> adminRoles = this.roleService.listByStatusInBatchIds(adminRoleIds, AssertEnum.NO.getCode());
        if (adminRoles == null || adminRoles.isEmpty()) {
            return null;
        }

        return adminRoles.stream()
                .map(RoleDO::getName)
                .collect(Collectors.toList());
    }

}

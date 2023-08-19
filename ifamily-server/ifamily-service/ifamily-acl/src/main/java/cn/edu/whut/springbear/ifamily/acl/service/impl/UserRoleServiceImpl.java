package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.UserRoleMapper;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.RoleDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.UserRoleDO;
import cn.edu.whut.springbear.ifamily.acl.service.RoleService;
import cn.edu.whut.springbear.ifamily.acl.service.UserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleDO> implements UserRoleService {

    private final RoleService roleService;

    @Override
    public List<String> listRoleNamesOfUser(Long userId) {
        // 查询出用户对应的所有角色 ID，注意去重
        QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct role_id").eq("user_id", userId);
        List<UserRoleDO> userRoleRelations = this.baseMapper.selectList(queryWrapper);
        if (userRoleRelations == null || userRoleRelations.isEmpty()) {
            return null;
        }

        // 过滤出用户对应的角色 ID 集合
        List<Long> userRoleIds = userRoleRelations.stream()
                .map(UserRoleDO::getRoleId)
                .collect(Collectors.toList());
        // 查询出用户对应的所有未禁用角色，并过滤出角色名称集合返回
        List<RoleDO> userRoles = this.roleService.listByStatusInBatchIds(userRoleIds, AssertEnum.NO.getCode());
        if (userRoles == null || userRoles.isEmpty()) {
            return null;
        }

        return userRoles.stream()
                .map(RoleDO::getName)
                .collect(Collectors.toList());
    }

}

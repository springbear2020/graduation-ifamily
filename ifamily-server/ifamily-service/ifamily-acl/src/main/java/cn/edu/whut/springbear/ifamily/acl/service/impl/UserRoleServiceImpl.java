package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.UserRoleMapper;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.UserRoleDO;
import cn.edu.whut.springbear.ifamily.acl.service.UserRoleService;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleDO> implements UserRoleService {

    @Override
    public void removeByRoleId(Long roleId) {
        UpdateWrapper<UserRoleDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("role_id", roleId);
        this.remove(updateWrapper);
    }

    @Override
    public List<Long> listRoleIdsOfUser(Long userId) {
        // 查询用户对应的所有角色关系
        QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<UserRoleDO> userRoleRelations = this.list(queryWrapper);
        if (CollUtil.isEmpty(userRoleRelations)) {
            return null;
        }

        // 过滤出用户对应的角色 ID 集合
        return userRoleRelations.stream()
                .map(UserRoleDO::getRoleId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> listUserIdsOfRole(Long roleId) {
        // 查询角色对应的所有用户关系
        QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", roleId);
        List<UserRoleDO> userRoleRelations = this.list(queryWrapper);
        if (CollUtil.isEmpty(userRoleRelations)) {
            return null;
        }

        // 过滤出角色对应的用户 ID 集合
        return userRoleRelations.stream()
                .map(UserRoleDO::getUserId)
                .collect(Collectors.toList());
    }

}

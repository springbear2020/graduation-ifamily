package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.user.mapper.UserRoleMapper;
import cn.edu.whut.springbear.ifamily.user.pojo.po.UserRoleDO;
import cn.edu.whut.springbear.ifamily.user.service.UserRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRoleDO> implements UserRoleService {

    @Override
    public List<Long> listRoleIdsOfUser(Long userId) {
        QueryWrapper<UserRoleDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct role_id").eq("user_id", userId);
        List<UserRoleDO> userRoleList = this.baseMapper.selectList(queryWrapper);
        return userRoleList.stream()
                .map(UserRoleDO::getRoleId)
                .collect(Collectors.toList());
    }

}

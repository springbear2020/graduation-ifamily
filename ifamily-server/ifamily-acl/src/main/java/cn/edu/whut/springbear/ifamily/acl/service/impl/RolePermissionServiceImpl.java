package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.RolePermissionMapper;
import cn.edu.whut.springbear.ifamily.acl.service.RolePermissionService;
import cn.edu.whut.springbear.ifamily.model.po.RolePermissionDO;
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
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermissionDO> implements RolePermissionService {

    @Override
    public List<Long> listPermissionIdsOfRoles(List<Long> roleIdList) {
        QueryWrapper<RolePermissionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct permission_id").in("role_id", roleIdList);
        List<RolePermissionDO> rolePermissionList = this.baseMapper.selectList(queryWrapper);
        return rolePermissionList.stream()
                .map(RolePermissionDO::getPermissionId)
                .collect(Collectors.toList());
    }

}

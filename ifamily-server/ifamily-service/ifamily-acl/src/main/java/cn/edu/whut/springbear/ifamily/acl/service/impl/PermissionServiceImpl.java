package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.PermissionMapper;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.acl.service.PermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionDO> implements PermissionService {

    @Override
    public PermissionDO getByStatusAndId(Long permissionId, Integer status) {
        QueryWrapper<PermissionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", permissionId).eq("status", status);
        return this.baseMapper.selectOne(queryWrapper);
    }

}

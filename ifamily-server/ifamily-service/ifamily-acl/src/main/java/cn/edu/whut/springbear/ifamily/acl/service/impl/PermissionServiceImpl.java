package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.PermissionMapper;
import cn.edu.whut.springbear.ifamily.acl.pojo.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.query.PermissionQuery;
import cn.edu.whut.springbear.ifamily.acl.service.PermissionService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

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
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean create(PermissionQuery permissionQuery) {
        PermissionDO permissionDO = new PermissionDO();
        BeanUtils.copyProperties(permissionQuery, permissionDO);
        permissionDO.setId(null);
        Date date = new Date();
        permissionDO.setCreated(date);
        permissionDO.setModified(date);
        return this.save(permissionDO);
    }

    @Override
    public boolean edit(PermissionQuery permissionQuery) {
        PermissionDO permissionDO = new PermissionDO();
        BeanUtils.copyProperties(permissionQuery, permissionDO);
        return this.updateById(permissionDO);
    }

}

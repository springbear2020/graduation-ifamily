package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.RoleMapper;
import cn.edu.whut.springbear.ifamily.acl.service.RoleService;
import cn.edu.whut.springbear.ifamily.model.po.RoleDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {
}

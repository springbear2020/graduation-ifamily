package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.user.mapper.RoleMapper;
import cn.edu.whut.springbear.ifamily.user.pojo.po.RoleDO;
import cn.edu.whut.springbear.ifamily.user.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleDO> implements RoleService {
}

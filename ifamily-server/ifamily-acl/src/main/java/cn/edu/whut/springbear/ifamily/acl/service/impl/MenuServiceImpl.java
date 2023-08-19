package cn.edu.whut.springbear.ifamily.acl.service.impl;

import cn.edu.whut.springbear.ifamily.acl.mapper.MenuMapper;
import cn.edu.whut.springbear.ifamily.acl.service.MenuService;
import cn.edu.whut.springbear.ifamily.model.po.MenuDO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @since 2023-03-20
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, MenuDO> implements MenuService {
}

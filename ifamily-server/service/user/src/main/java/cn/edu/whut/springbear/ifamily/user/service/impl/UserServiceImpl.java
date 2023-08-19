package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.domain.po.User;
import cn.edu.whut.springbear.ifamily.user.mapper.UserMapper;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public Boolean saveUser(User user) {
        return baseMapper.insert(user) == 1;
    }
}

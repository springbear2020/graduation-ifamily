package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.user.mapper.UserLoginLogMapper;
import cn.edu.whut.springbear.ifamily.user.pojo.po.UserLoginLogDO;
import cn.edu.whut.springbear.ifamily.user.service.UserLoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLogDO> implements UserLoginLogService {

    @Override
    public Boolean create(UserLoginLogDO userLoginLogDO) {
        return this.baseMapper.insert(userLoginLogDO) == 1;
    }

}

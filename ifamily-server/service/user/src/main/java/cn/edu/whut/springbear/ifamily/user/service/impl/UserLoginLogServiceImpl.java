package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.domain.po.UserLoginLog;
import cn.edu.whut.springbear.ifamily.user.mapper.UserLoginLogMapper;
import cn.edu.whut.springbear.ifamily.user.service.UserLoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog> implements UserLoginLogService {
}

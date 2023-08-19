package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.domain.po.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
public interface UserService extends IService<User> {
    /**
     * 保存用户
     *
     * @param user User
     * @return [true]保存成功
     */
    Boolean saveUser(User user);
}

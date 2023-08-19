package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.user.pojo.po.UserDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
public interface UserService extends IService<UserDO> {

    /**
     * 根据用户名查询用户
     */
    UserDTO loadUserByUsername(String username);

}

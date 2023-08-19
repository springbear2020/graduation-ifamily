package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.user.pojo.po.UserLoginLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
public interface UserLoginLogService extends IService<UserLoginLogDO> {

    /**
     * 新增用户登录记录
     *
     * @param userLoginLogDO 用户登录记录
     * @return [true]新增成功
     */
    Boolean create(UserLoginLogDO userLoginLogDO);

}

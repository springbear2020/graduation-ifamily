package cn.edu.whut.springbear.ifamily.manager.service;

import cn.edu.whut.springbear.ifamily.model.po.UserDO;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 10:30
 */
public interface SecurityUserService {

    /**
     * 获取当前已认证用户
     */
    UserDO current();

}

package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.user.pojo.po.UsernameUpdateLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 23/03/25 17:33
 */
public interface UsernameUpdateLogService extends IService<UsernameUpdateLogDO> {

    /**
     * 获取用户最新的用户名修改记录
     */
    UsernameUpdateLogDO getLatestOfUser(Long userId);

    /**
     * 保存用户用户名修改记录
     */
    boolean create(String oldName, String newName, Long userId);

}

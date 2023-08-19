package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.user.pojo.po.UsernameLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 23/03/25 17:33
 */
public interface UsernameLogService extends IService<UsernameLogDO> {

    /**
     * 获取用户最新的用户名修改记录
     */
    UsernameLogDO getLatest(Long userId);

    /**
     * 保存用户名修改记录
     */
    boolean create(String oldUsername, String newUsername, Long userId);

}

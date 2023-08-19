package cn.edu.whut.springbear.ifamily.backend.service;

import cn.edu.whut.springbear.ifamily.backend.pojo.po.LoginLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Spring-_-Bear
 * @since 23/05/08 11:26
 */
public interface LoginLogService extends IService<LoginLogDO> {

    /**
     * 新增管理员登录记录
     *
     * @param adminId 管理员 ID
     * @return [true]保存成功
     */
    boolean create(Long adminId);

}

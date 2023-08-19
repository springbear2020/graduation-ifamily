package cn.edu.whut.springbear.ifamily.manager.service;

import cn.edu.whut.springbear.ifamily.manager.pojo.po.CodeLogDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 2023-03-23
 */
public interface CodeLogService extends IService<CodeLogDO> {

    /**
     * 查询指定日期、指定接收者的验证码发送记录
     *
     * @param receiver 接收者
     * @param date     指定日期
     * @return 指定日期下指定接收者的验证码发送记录
     */
    List<CodeLogDO> listByDateAndReceiver(String receiver, Date date);

}

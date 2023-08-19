package cn.edu.whut.springbear.ifamily.user.pojo.po;

import cn.edu.whut.springbear.ifamily.model.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 09:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("login_log")
public class LoginLogDO extends AbstractBaseDO {

    private static final long serialVersionUID = -6298765208719067231L;

    /**
     * IP 地址
     */
    private String ip;

    /**
     * IP 归属地
     */
    private String location;

    /**
     * 登录设备名称
     */
    private String device;

    /**
     * 登录时间
     */
    private Date loginDatetime;

    /**
     * 用户 ID
     */
    private Long userId;

}
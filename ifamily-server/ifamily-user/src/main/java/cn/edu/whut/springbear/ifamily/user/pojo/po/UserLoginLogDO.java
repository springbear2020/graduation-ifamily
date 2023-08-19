package cn.edu.whut.springbear.ifamily.user.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 09:56
 */
@Data
@TableName("user_login_log")
public class UserLoginLogDO {

    private static final long serialVersionUID = -6298765208719067231L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 是否删除：[0]未删除 [1]已删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer deleted;

    /**
     * 用户 ID
     */
    private Long userId;

}

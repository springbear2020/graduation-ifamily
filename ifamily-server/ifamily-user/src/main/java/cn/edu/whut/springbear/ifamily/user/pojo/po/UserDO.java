package cn.edu.whut.springbear.ifamily.user.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 09:52
 */
@Data
@TableName("user")
public class UserDO {

    private static final long serialVersionUID = 6809861721356316654L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像图片地址
     */
    private String avatar;

    /**
     * 个性签名
     */
    private String signature;

    /**
     * 用户状态：[0]正常 [1]异常
     */
    private Integer status;

    /**
     * 上次登录时间
     */
    private Date lastLogin;

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

}

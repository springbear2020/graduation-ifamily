package cn.edu.whut.springbear.ifamily.backend.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 09:52
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("backend")
public class AdminDO extends AbstractBaseDO {

    private static final long serialVersionUID = 6809861721356316654L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 管理员昵称
     */
    private String nickname;

    /**
     * 管理员头像图片地址
     */
    private String avatar;

    /**
     * 账号禁用状态：[0]启用 [1]禁用
     */
    private Integer status;

    /**
     * 上次登录时间
     */
    private Date lastLogin;

}

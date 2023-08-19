package cn.edu.whut.springbear.ifamily.domain.po;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 09:52
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User extends AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = 6767612194764139278L;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 手机
     */
    @ApiModelProperty("手机")
    private String phone;

    /**
     * 邮箱
     */
    @ApiModelProperty("邮箱")
    private String email;

    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickname;

    /**
     * 用户头像图片地址
     */
    @ApiModelProperty("用户头像图片地址")
    private String avatar;

    /**
     * 个性签名
     */
    @ApiModelProperty("个性签名")
    private String signature;

    /**
     * 用户状态：[0]正常 [1]异常
     */
    @ApiModelProperty("用户状态：[0]正常 [1]异常")
    private Integer status;

    /**
     * 上次登录时间
     */
    @ApiModelProperty("上次登录时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLogin;
}

package cn.edu.whut.springbear.ifamily.user.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Spring-_-Bear
 * @since 23/03/22 09:53
 */
@Data
public class UserLoginQuery {

    /**
     * 密码登录
     */
    public static final String LOGIN_TYPE_PASSWORD = "0";
    /**
     * 验证码登录
     */
    public static final String LOGIN_TYPE_CODE = "1";

    @ApiModelProperty("账户：用户名 | 手机 | 邮箱")
    @NotBlank(message = "账户名不能为空")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    private String code;

    @ApiModelProperty("登录方式：[0]密码登录 [1]验证码登录")
    @Pattern(regexp = "[0-1]", message = "登录方式：[0]密码登录 [1]验证码登录")
    private String loginType;

}

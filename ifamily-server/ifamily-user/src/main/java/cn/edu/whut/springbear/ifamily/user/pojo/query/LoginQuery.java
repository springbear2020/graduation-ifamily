package cn.edu.whut.springbear.ifamily.user.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Spring-_-Bear
 * @since 23/03/22 09:53
 */
@Data
public class LoginQuery {

    @ApiModelProperty("账户：用户名 | 手机 | 邮箱")
    @NotBlank(message = "账户名值不能为空")
    private String account;

    /**
     * 密码与验证码二选一
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 密码与验证码二选一
     */
    @ApiModelProperty("验证码")
    @Pattern(regexp = "^\\d{6}$", message = "验证码为 6 位长度数字")
    private String code;

    @ApiModelProperty("登录方式：[0]密码登录 [1]验证码登录")
    @Range(max = 1L, message = "登录方式：[0]密码登录 [1]验证码登录")
    @NotNull(message = "登录方式值不能为空")
    private Integer loginType;

}

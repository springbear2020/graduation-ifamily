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

    @ApiModelProperty(value = "账户名：用户名 | 手机 | 邮箱", required = true)
    @NotBlank(message = "账户名值不能为空")
    private String account;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    @Pattern(regexp = "^\\d{6}$", message = "验证码为 6 位长度数字")
    private String code;

    @ApiModelProperty(value = "登录方式：[1]密码登录 [2]验证码登录", required = true)
    @Range(min = 1L, max = 2L, message = "登录方式：[1]密码登录 [2]验证码登录")
    @NotNull(message = "登录方式值不能为空")
    private Integer loginType;

}

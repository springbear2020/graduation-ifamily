package cn.edu.whut.springbear.ifamily.user.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author Spring-_-Bear
 * @since 23/03/23 15:52
 */
@Data
public class ResetQuery {

    @ApiModelProperty(value = "账户：手机 | 邮箱", required = true)
    @NotBlank(message = "账户名值不能为空")
    private String account;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码值不能为空")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,16}$", message = "密码限 6-16 个字符，至少一个字母和数字")
    private String password;

    @ApiModelProperty(value = "验证码", required = true)
    @NotBlank(message = "验证码值不能为空")
    @Pattern(regexp = "^\\d{6}$", message = "验证码为 6 位长度数字")
    private String code;

}


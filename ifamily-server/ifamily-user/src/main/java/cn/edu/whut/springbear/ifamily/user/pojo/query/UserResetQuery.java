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
public class UserResetQuery {

    @ApiModelProperty("账户：手机 | 邮箱")
    @NotBlank(message = "账户名不能为空")
    private String account;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty("验证码")
    @Pattern(regexp = "^\\d{6}$", message = "验证码为 6 位长度数字")
    private String code;

}

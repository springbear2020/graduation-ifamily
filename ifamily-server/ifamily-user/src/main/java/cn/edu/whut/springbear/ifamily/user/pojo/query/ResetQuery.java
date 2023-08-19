package cn.edu.whut.springbear.ifamily.user.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author Spring-_-Bear
 * @since 23/03/23 15:52
 */
@Data
public class ResetQuery {

    @ApiModelProperty("账户：手机 | 邮箱")
    @NotBlank(message = "账户名值不能为空")
    private String account;

    @ApiModelProperty("密码")
    @NotBlank(message = "密码值不能为空")
    @Size(max = 30, message = "请填写密码, 长度不大于 30")
    private String password;

    @ApiModelProperty("验证码")
    @Pattern(regexp = "^\\d{6}$", message = "验证码为 6 位长度数字")
    @NotBlank(message = "验证码值不能为空")
    private String code;

}

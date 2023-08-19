package cn.edu.whut.springbear.ifamily.user.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author Spring-_-Bear
 * @since 23/03/22 09:53
 */
@Data
public class LoginQuery {

    @ApiModelProperty(value = "账户名：用户名 | 手机 | 邮箱", required = true)
    @NotBlank(message = "账户名值不能为空")
    private String account;

    @ApiModelProperty(value = "密码", required = true)
    @NotBlank(message = "密码值不能为空")
    private String password;

}

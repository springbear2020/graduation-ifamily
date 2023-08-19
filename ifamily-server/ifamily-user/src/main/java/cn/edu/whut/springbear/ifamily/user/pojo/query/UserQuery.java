package cn.edu.whut.springbear.ifamily.user.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Spring-_-Bear
 * @since 23/03/20 15:14
 */
@Data
public class UserQuery implements Serializable {

    private static final long serialVersionUID = -7980845198352643558L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机")
    private String phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户头像图片地址")
    private String avatar;

    @ApiModelProperty("个性签名")
    private String signature;

}

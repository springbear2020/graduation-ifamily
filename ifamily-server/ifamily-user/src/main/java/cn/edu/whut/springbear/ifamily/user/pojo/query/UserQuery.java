package cn.edu.whut.springbear.ifamily.user.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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

    @ApiModelProperty("用户昵称")
    @Size(max = 30, message = "请填写用户昵称, 长度不大于 30")
    private String nickname;

    @ApiModelProperty("个性签名")
    @Size(max = 30, message = "请填写个性签名, 长度不大于 30")
    private String signature;

    @ApiModelProperty("用户头像图片地址")
    @Pattern(regexp = "^((https?|http|ftp|file)://)?([\\da-z.-]+)\\.([a-z.]{2,6})([/\\w .-]*)*/?$", message = "请上传您的用户头像图片")
    @Size(max = 250, message = "图片地址长度不大于 250")
    private String avatar;

}

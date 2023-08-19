package cn.edu.whut.springbear.ifamily.genealogy.pojo.query;

import cn.edu.whut.springbear.ifamily.common.constant.RegExpConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 16:00
 */
@Data
public class GenealogyQuery implements Serializable {

    private static final long serialVersionUID = 6101305342048604082L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty(value = "家族封面图片", required = true)
    @Pattern(regexp = RegExpConstants.URL_PATTERN, message = "请上传您的家族封面图片")
    @Size(max = 250, message = "图片地址长度不大于 250")
    @NotBlank(message = "请上传您的家族封面图片")
    private String cover;

    @ApiModelProperty(value = "家族名称", required = true)
    @Size(max = 30, message = "请填写家族名称, 长度不大于 30")
    @NotBlank(message = "家族名称值不能为空")
    private String title;

    @ApiModelProperty(value = "家族姓氏", required = true)
    @Size(max = 30, message = "请填写家族姓氏, 长度不大于 30")
    @NotBlank(message = "家族姓氏值不能为空")
    private String surname;

    @ApiModelProperty(value = "家族地址", required = true)
    @Size(max = 250, message = "请填写家族地址，长度不大于 250")
    @NotBlank(message = "家族地址值不能为空")
    private String address;

    @ApiModelProperty("祖籍地址")
    @Size(max = 250, message = "请填写祖籍地址，长度不大于 250")
    private String ancestryAddress;

    @ApiModelProperty("家族简介")
    @Size(max = 1000, message = "请填写家族简介，长度不大于 1000")
    private String introduction;

    @ApiModelProperty("字辈歌")
    @Size(max = 1000, message = "请填写家族字辈歌，长度不大于 1000")
    private String generationSong;

}

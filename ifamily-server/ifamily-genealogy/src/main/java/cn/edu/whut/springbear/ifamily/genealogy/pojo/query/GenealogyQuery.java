package cn.edu.whut.springbear.ifamily.genealogy.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
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

    @ApiModelProperty("封面图片地址")
    @NotBlank(message = "请上传您的家族封面图片")
    private String cover;

    @ApiModelProperty("家族名称")
    @NotBlank(message = "请填写家族名称, 长度不大于 30")
    private String title;

    @ApiModelProperty("家族姓氏")
    @NotBlank(message = "请填写家族姓氏, 长度不大于 30")
    private String surname;

    @ApiModelProperty("家族地址")
    @NotBlank(message = "请选择家族地址")
    private String address;

    @ApiModelProperty("祖籍地址")
    private String ancestryAddress;

    @ApiModelProperty("家族简介")
    private String introduction;

    @ApiModelProperty("字辈歌")
    private String generationSong;

}

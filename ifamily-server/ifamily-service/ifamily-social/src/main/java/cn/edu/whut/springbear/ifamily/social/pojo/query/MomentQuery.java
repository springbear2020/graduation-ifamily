package cn.edu.whut.springbear.ifamily.social.pojo.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 17:32
 */
@Data
public class MomentQuery implements Serializable {

    private static final long serialVersionUID = 6365716258705385940L;

    @ApiModelProperty("动态 ID")
    private Long id;

    @ApiModelProperty(value = "动态内容", required = true)
    @Size(max = 1000, message = "请填写动态内容, 长度不大于 1000")
    @NotBlank(message = "动态内容值不能为空")
    private String content;

    @ApiModelProperty(value = "权限设置：[0]仅自己可见 [1]默认家族成员可见", required = true)
    @Range(max = 1L, message = "权限设置：[0]仅自己可见 [1]默认家族成员可见")
    @NotNull(message = "权限设置值不能为空")
    private Integer whoCanSee;

    @JsonProperty("imgUrls")
    @ApiModelProperty("动态图片列表")
    private List<String> imgUrls;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @ApiModelProperty(value = "定时发表时间：yyyy-MM-dd HH:mm", required = true)
    @NotNull(message = "定时发表时间值不能为空")
    private Date scheduled;

}

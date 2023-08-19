package cn.edu.whut.springbear.ifamily.genealogy.pojo.query;

import cn.edu.whut.springbear.ifamily.common.constant.RegExpConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 12:31
 */
@Data
public class MemorabiliaQuery implements Serializable {

    private static final long serialVersionUID = 4152935933041937096L;

    @ApiModelProperty(value = "家族大事标题", required = true)
    @Size(max = 100, message = "请填写家族大事标题, 长度不大于 100")
    @NotBlank(message = "家族大事标题值不能为空")
    private String title;

    @JsonFormat(pattern = "yyyy")
    @ApiModelProperty(value = "家族大事发生年份：yyyy", required = true)
    @NotNull(message = "家族大事发生年份值不能为空")
    private Date occurredYear;

    @ApiModelProperty(value = "家族大事配图", required = true)
    @Pattern(regexp = RegExpConstants.URL_PATTERN, message = "请上传家族大事配图图片")
    @Size(max = 250, message = "图片地址长度不大于 250")
    @NotBlank(message = "请上传家族大事配图图片")
    private String cover;

    @ApiModelProperty(value = "家族大事内容", required = true)
    @Size(max = 1000, message = "请填写家族大事内容, 长度不大于 1000")
    @NotBlank(message = "家族大事内容值不能为空")
    private String content;

}

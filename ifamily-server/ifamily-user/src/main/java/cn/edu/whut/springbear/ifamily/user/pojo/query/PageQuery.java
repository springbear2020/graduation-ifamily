package cn.edu.whut.springbear.ifamily.user.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Spring-_-Bear
 * @since 23/03/24 21:54
 */
@Data
public class PageQuery {

    @ApiModelProperty("当前页码")
    @Min(value = 1L, message = "当前页面不能小于 1")
    @NotNull
    private Integer current;

    @ApiModelProperty("每页显示的数量")
    @Min(value = 1L, message = "每页显示的数量不能小于 1")
    @Max(value = 20L, message = "每页显示的数量不能大于 20")
    @NotNull
    private Integer size;

}

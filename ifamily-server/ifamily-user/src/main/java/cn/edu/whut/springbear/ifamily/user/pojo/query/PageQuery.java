package cn.edu.whut.springbear.ifamily.user.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Spring-_-Bear
 * @since 23/03/24 21:54
 */
@Data
public class PageQuery {

    @ApiModelProperty("当前页码")
    @Min(value = 1L, message = "当前页码值不能小于 1")
    @NotNull(message = "当前页码值不能为空")
    private Integer current;

    @ApiModelProperty("每页显示的数量")
    @Range(min = 1L, max = 20L, message = "每页显示的数量值范围是：[1, 20]")
    @NotNull(message = "每页显示的数量值不能为空")
    private Integer size;

}

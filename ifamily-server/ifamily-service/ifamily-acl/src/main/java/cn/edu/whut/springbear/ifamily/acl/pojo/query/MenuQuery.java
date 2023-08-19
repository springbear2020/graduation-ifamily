package cn.edu.whut.springbear.ifamily.acl.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Spring-_-Bear
 * @since 23/05/06 15:27
 */
@Data
public class MenuQuery implements Serializable {

    private static final long serialVersionUID = -2643812756652280705L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty(value = "路由路径", required = true)
    @Size(max = 50, message = "请填写路由路径, 长度不大于 50")
    @NotBlank(message = "路由路径值不能为空")
    private String path;

    @ApiModelProperty(value = "菜单名称", required = true)
    @Size(max = 50, message = "请填写菜单名称, 长度不大于 50")
    @NotBlank(message = "菜单名称值不能为空")
    private String title;

    @ApiModelProperty(value = "菜单描述", required = true)
    @Size(max = 100, message = "请填写菜单描述, 长度不大于 100")
    @NotBlank(message = "菜单描述值不能为空")
    private String description;

    @ApiModelProperty(value = "是否已禁用：[0]否 [1]是", required = true)
    @Range(max = 1L, message = "是否已禁用：[0]否 [1]是")
    @NotNull(message = "是否已禁用值不能为空")
    private Integer status;

}

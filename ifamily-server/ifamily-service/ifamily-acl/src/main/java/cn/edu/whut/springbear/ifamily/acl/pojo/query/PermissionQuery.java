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
 * @since 23/05/05 22:14
 */
@Data
public class PermissionQuery implements Serializable {

    private static final long serialVersionUID = 6052358764256065354L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty(value = "权限路径", required = true)
    @Size(max = 50, message = "请填写权限路径, 长度不大于 50")
    @NotBlank(message = "权限路径值不能为空")
    private String path;

    @ApiModelProperty(value = "权限名称", required = true)
    @Size(max = 50, message = "请填写权限名称, 长度不大于 50")
    @NotBlank(message = "权限名称值不能为空")
    private String name;

    @ApiModelProperty(value = "权限描述", required = true)
    @Size(max = 100, message = "请填写权限描述, 长度不大于 100")
    @NotBlank(message = "权限描述值不能为空")
    private String description;

    @ApiModelProperty(value = "是否禁用：[0]否 [1]是", required = true)
    @Range(max = 1L, message = "是否禁用：[0]否 [1]是")
    @NotNull(message = "权限禁用状态值不能为空")
    private Integer status;

}

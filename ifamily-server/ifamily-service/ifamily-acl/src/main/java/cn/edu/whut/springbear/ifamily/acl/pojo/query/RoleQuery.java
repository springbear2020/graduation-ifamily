package cn.edu.whut.springbear.ifamily.acl.pojo.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/05/07 08:21
 */
@Data
public class RoleQuery implements Serializable {

    private static final long serialVersionUID = -6433023558887686002L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty(value = "角色名称", required = true)
    @Size(max = 50, message = "请填写角色名称, 长度不大于 50")
    @NotBlank(message = "角色名称值不能为空")
    private String name;

    @ApiModelProperty(value = "角色描述", required = true)
    @Size(max = 100, message = "请填写角色描述, 长度不大于 100")
    @NotBlank(message = "角色描述值不能为空")
    private String description;

    @ApiModelProperty(value = "是否已禁用：[0]否 [1]是", required = true)
    @Range(max = 1L, message = "是否已禁用：[0]否 [1]是")
    @NotNull(message = "是否已禁用值不能为空")
    private Integer status;

    @JsonProperty("permissionIds")
    @ApiModelProperty("角色权限资源 ID 列表")
    private List<Long> permissionIds;

    @JsonProperty("menuIds")
    @ApiModelProperty("角色菜单资源 ID 列表")
    private List<Long> menuIds;

}

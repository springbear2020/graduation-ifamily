package cn.edu.whut.springbear.ifamily.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 22:27
 */
@Data
@TableName("role_permission")
public class RolePermissionDO implements Serializable {

    private static final long serialVersionUID = 5058489921797195670L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 是否删除：[0]未删除 [1]已删除
     */
    @TableLogic
    @TableField("is_deleted")
    private Integer deleted;

    /**
     * 角色 ID
     */
    private Long roleId;

    /**
     * 权限 ID
     */
    private Long permissionId;

}

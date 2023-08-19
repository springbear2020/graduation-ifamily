package cn.edu.whut.springbear.ifamily.acl.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 22:27
 */
@Data
@TableName("role_permission_relation")
public class RolePermissionDO {

    private static final long serialVersionUID = 5058489921797195670L;

    /**
     * 角色 ID
     */
    private Long roleId;

    /**
     * 权限 ID
     */
    private Long permissionId;

}

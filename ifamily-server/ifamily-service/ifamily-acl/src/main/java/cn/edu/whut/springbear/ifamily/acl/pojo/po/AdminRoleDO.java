package cn.edu.whut.springbear.ifamily.acl.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 22:25
 */
@Data
@TableName("admin_role_relation")
public class AdminRoleDO {

    private static final long serialVersionUID = 4533422004787303771L;

    /**
     * 管理员 ID
     */
    private Long adminId;

    /**
     * 角色 ID
     */
    private Long roleId;

}

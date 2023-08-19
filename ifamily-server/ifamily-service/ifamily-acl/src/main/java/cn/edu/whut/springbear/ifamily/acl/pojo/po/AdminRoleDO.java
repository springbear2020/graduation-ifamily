package cn.edu.whut.springbear.ifamily.acl.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 22:25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("admin_role_relation")
public class AdminRoleDO extends AbstractBaseDO {

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

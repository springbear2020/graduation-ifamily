package cn.edu.whut.springbear.ifamily.acl.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 22:25
 */
@Data
@TableName("user_role_relation")
public class UserRoleDO {

    private static final long serialVersionUID = 4533422004787303771L;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 角色 ID
     */
    private Long roleId;

}

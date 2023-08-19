package cn.edu.whut.springbear.ifamily.acl.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 22:27
 */
@Data
@TableName("role_menu_relation")
public class RoleMenuDO {

    private static final long serialVersionUID = 5058489921797195670L;

    /**
     * 角色 ID
     */
    private Long roleId;

    /**
     * 菜单 ID
     */
    private Long menuId;

}

package cn.edu.whut.springbear.ifamily.acl.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 22:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role_menu_relation")
public class RoleMenuDO extends AbstractBaseDO {

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
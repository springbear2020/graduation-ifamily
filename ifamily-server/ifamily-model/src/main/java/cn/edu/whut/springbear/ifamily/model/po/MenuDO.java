package cn.edu.whut.springbear.ifamily.model.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 22:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("menu")
public class MenuDO extends AbstractBaseDO {

    private static final long serialVersionUID = 1765774877595258661L;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单描述
     */
    private String description;

    /**
     * 菜单值：模块:控制器:操作[user:login:create]
     */
    private String value;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单类型：[1]目录 [2]菜单 [3]按钮
     */
    private Integer type;

    /**
     * 菜单路由路径
     */
    private String path;

    /**
     * 菜单状态：[0]启用 [1]禁用
     */
    private Integer status;

    /**
     * 父级菜单 ID
     */
    private Long parentId;

}

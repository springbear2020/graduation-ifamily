package cn.edu.whut.springbear.ifamily.user.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 22:38
 */
@Data
@TableName("menu")
public class MenuDO implements Serializable {

    private static final long serialVersionUID = 1765774877595258661L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父级菜单 ID
     */
    private Long parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单描述
     */
    private String desc;

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

}

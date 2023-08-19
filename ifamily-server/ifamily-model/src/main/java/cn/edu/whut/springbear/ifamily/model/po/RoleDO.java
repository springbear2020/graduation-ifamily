package cn.edu.whut.springbear.ifamily.model.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 22:21
 */
@Data
@TableName("role")
public class RoleDO implements Serializable {

    private static final long serialVersionUID = 5491593211477785302L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色状态：[0]启用 [1]禁用
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

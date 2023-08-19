package cn.edu.whut.springbear.ifamily.acl.pojo.po;

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
public class MenuDO extends AbstractCommonDO {

    private static final long serialVersionUID = 1765774877595258661L;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 菜单描述
     */
    private String description;

    /**
     * 是否已禁用：[0]否 [1]是
     */
    private Integer status;

}

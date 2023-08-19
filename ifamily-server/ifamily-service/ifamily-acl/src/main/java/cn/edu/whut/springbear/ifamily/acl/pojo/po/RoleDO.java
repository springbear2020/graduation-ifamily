package cn.edu.whut.springbear.ifamily.acl.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 22:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("role")
public class RoleDO extends AbstractCommonDO {

    private static final long serialVersionUID = 5491593211477785302L;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 角色禁用状态：[0]启用 [1]禁用
     */
    private Integer status;

}

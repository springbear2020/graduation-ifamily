package cn.edu.whut.springbear.ifamily.acl.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 22:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("permission")
public class PermissionDO extends AbstractBaseDO {

    private static final long serialVersionUID = 808668047951260729L;

    /**
     * 权限路径
     */
    private String path;

    /**
     * 权限名称
     */
    private String name;

    /**
     * 权限描述
     */
    private String description;

    /**
     * 权限禁用状态：[0]启用 [1]禁用
     */
    private Integer status;

}

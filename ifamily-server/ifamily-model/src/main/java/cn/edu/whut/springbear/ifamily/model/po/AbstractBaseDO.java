package cn.edu.whut.springbear.ifamily.model.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 09:39
 */
@Data
public abstract class AbstractBaseDO implements Serializable {

    private static final long serialVersionUID = -7246624737179621261L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;

    /**
     * 创建时间
     */
    protected Date created;

    /**
     * 修改时间
     */
    protected Date modified;

    /**
     * 是否删除：[0]未删除 [1]已删除
     */
    @TableLogic
    @TableField("is_deleted")
    protected Integer deleted;

}

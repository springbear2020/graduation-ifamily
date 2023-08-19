package cn.edu.whut.springbear.ifamily.acl.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 09:39
 */
@Data
public abstract class AbstractCommonDO implements Serializable {

    private static final long serialVersionUID = -7246624737179621261L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    protected Long id;

    /**
     * 创建时间
     */
    protected Date created;

    /**
     * 修改时间
     */
    protected Date modified;

}

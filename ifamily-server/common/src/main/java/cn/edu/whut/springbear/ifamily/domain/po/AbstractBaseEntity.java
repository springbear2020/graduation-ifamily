package cn.edu.whut.springbear.ifamily.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 09:53
 */
@Data
public abstract class AbstractBaseEntity implements Serializable {

    private static final long serialVersionUID = -3813789564267425176L;

    /**
     * ID
     */
    @ApiModelProperty("ID")
    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date created;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date modified;

    /**
     * 是否删除：[0]未删除 [1]已删除
     */
    @ApiModelProperty("是否删除：[0]未删除 [1]已删除")
    @TableField("is_deleted")
    @TableLogic
    protected Integer deleted;
}

package cn.edu.whut.springbear.ifamily.user.pojo.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/25 17:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("username_update_log")
public class UsernameUpdateLogDO implements Serializable {

    private static final long serialVersionUID = -1135906574043861389L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 更新前的用户名
     */
    private String oldUsername;

    /**
     * 更新后的用户名
     */
    private String newUsername;

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

    /**
     * 用户 ID
     */
    private Long userId;

}

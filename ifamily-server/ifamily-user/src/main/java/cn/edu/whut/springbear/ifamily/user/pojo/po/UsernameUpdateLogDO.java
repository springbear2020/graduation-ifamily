package cn.edu.whut.springbear.ifamily.user.pojo.po;

import cn.edu.whut.springbear.ifamily.model.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/03/25 17:29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("username_update_log")
public class UsernameUpdateLogDO extends AbstractBaseDO {

    private static final long serialVersionUID = -1135906574043861389L;

    /**
     * 更新前的用户名
     */
    private String oldUsername;

    /**
     * 更新后的用户名
     */
    private String newUsername;

    /**
     * 用户 ID
     */
    private Long userId;

}

package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.model.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户 - 家族关联表
 *
 * @author Spring-_-Bear
 * @since 23/03/29 10:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user_genealogy")
public class UserGenealogyDO extends AbstractBaseDO {

    private static final long serialVersionUID = 3170966840978749550L;

    /**
     * 是否用户默认家族：[0]否 [1]是
     */
    private Integer defaultGenealogy;

    /**
     * 是否家族管理员：[0]否 [1]是
     */
    private Integer genealogyAdmin;

    /**
     * 家族 ID
     */
    private Long genealogyId;

    /**
     * 用户 ID
     */
    private Long userId;

}

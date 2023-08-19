package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user_genealogy_relation")
public class UserGenealogyDO extends AbstractBaseDO {

    private static final long serialVersionUID = 3170966840978749550L;

    /**
     * 是否用户默认家族：[0]否 [1]是
     */
    @TableField("is_default_genealogy")
    private Integer defaultGenealogy;

    /**
     * 用户是否为家族管理员：[0]否 [1]是
     */
    @TableField("is_genealogy_admin")
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

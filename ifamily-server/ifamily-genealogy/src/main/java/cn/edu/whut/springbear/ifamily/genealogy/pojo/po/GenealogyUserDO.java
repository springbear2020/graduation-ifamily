package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.model.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 10:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("genealogy_user")
public class GenealogyUserDO extends AbstractBaseDO {

    private static final long serialVersionUID = 3170966840978749550L;

    /**
     * 是否用户默认家族：[0]不是 [1]是
     */
    private Integer defaultGenealogy;

    /**
     * 家族 ID
     */
    private Long genealogyId;

    /**
     * 用户 ID
     */
    private Long userId;

}

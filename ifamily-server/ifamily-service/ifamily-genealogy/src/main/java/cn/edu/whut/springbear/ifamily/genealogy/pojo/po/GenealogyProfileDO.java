package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 09:38
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("genealogy_profile")
public class GenealogyProfileDO extends AbstractBaseDO {

    private static final long serialVersionUID = -921672694929476331L;

    /**
     * 总人数
     */
    private Long total;

    /**
     * 男性人数
     */
    private Long male;

    /**
     * 女性人数
     */
    private Long female;

    /**
     * 健在人数
     */
    private Long alive;

    /**
     * 已逝人数
     */
    private Long death;

    /**
     * 家族 ID
     */
    private Long genealogyId;

}

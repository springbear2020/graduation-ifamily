package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.model.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/04/01 19:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("genealogy_admin")
public class GenealogyAdminDO extends AbstractBaseDO {

    private static final long serialVersionUID = 4457148393523267479L;

    /**
     * 家族 ID
     */
    private Long genealogyId;

    /**
     * 用户 ID
     */
    private Long userId;

}

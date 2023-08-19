package cn.edu.whut.springbear.ifamily.backend.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/05/27 07:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("db_table")
public class DbTableDO extends AbstractBaseDO {

    private static final long serialVersionUID = 6933196586362240399L;

    private String name;

    private String description;

    private Long parentId;

}

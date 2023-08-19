package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.model.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 家族修谱日志
 *
 * @author Spring-_-Bear
 * @since 23/04/07 11:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("revision_log")
public class RevisionLogDO extends AbstractBaseDO {

    private static final long serialVersionUID = 239383198215697473L;

    /**
     * 操作类型：[1]新增 [2]删除 [3]编辑 [4]查看
     */
    private Integer operationType;

    /**
     * 操作日期
     */
    private Date operationDate;

    /**
     * 被操作人员姓名
     */
    private String operatedPeopleName;

    /**
     * 操作者用户 ID
     */
    private Long operatorUserId;

    /**
     * 被操作者家族人员 ID
     */
    private Long operatedPeopleId;

    /**
     * 家族 ID
     */
    private Long genealogyId;

}

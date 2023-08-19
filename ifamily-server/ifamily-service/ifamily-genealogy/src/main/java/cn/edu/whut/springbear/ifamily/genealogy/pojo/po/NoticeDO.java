package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/04/09 17:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("genealogy_notice")
public class NoticeDO extends AbstractBaseDO {

    private static final long serialVersionUID = -6366395807219122367L;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 是否已审核：[0]否 [1]是
     */
    @TableField("is_approval")
    private Integer approval;

    /**
     * 审核时间
     */
    private Date reviewDatetime;

    /**
     * 家族 ID
     */
    private Long genealogyId;

    /**
     * 发布者用户 ID
     */
    private Long announcerUserId;

    /**
     * 审核者用户 ID
     */
    private Long inspectorUserId;

}

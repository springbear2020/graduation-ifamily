package cn.edu.whut.springbear.ifamily.social.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/04/30 11:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("moment_comment")
public class MomentCommentDO extends AbstractBaseDO {

    private static final long serialVersionUID = -3694561648130096293L;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 动态 ID
     */
    private Long momentId;

    /**
     * 评论者用户 ID
     */
    private Long sourceUserId;

    /**
     * 被回复者用户 ID
     */
    private Long targetUserId;

    /**
     * 父级评论 ID
     */
    private Long parentId;

}

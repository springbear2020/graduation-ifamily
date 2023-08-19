package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     * 家族 ID
     */
    private Long genealogyId;

    /**
     * 发布者用户 ID
     */
    private Long announcerUserId;

}

package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.model.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 家族访问日志
 *
 * @author Spring-_-Bear
 * @since 23/04/07 11:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("visitor_log")
public class VisitorLogDO extends AbstractBaseDO {

    private static final long serialVersionUID = 239383198215697473L;

    /**
     * 访问日期
     */
    private Date visitedDate;

    /**
     * 访问者用户 ID
     */
    private Long visitorUserId;

    /**
     * 被访问的家族 ID
     */
    private Long genealogyId;

}

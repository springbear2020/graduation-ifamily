package cn.edu.whut.springbear.ifamily.social.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 16:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("moment")
public class MomentDO extends AbstractBaseDO {

    private static final long serialVersionUID = 5553011221223185042L;

    /**
     * 动态内容
     */
    private String content;

    /**
     * 权限设置：[0]仅自己可见 [1]默认家族成员可见
     */
    private Integer whoCanSee;

    /**
     * 定时发表时间
     */
    private Date scheduled;

    /**
     * 发布者用户 ID
     */
    private Long postUserId;

    /**
     * 可查看当前动态的人员家族 ID
     */
    private Long genealogyId;

}

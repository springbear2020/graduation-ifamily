package cn.edu.whut.springbear.ifamily.social.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/04/29 19:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("moment_like")
public class MomentLikeDO extends AbstractBaseDO {

    private static final long serialVersionUID = -94711589666131917L;

    /**
     * 点赞用户用户名
     */
    private String username;

    /**
     * 点赞用户昵称
     */
    private String nickname;

    /**
     * 点赞用户头像图片地址
     */
    private String avatar;

    /**
     * 动态点赞用户 ID
     */
    private Long userId;

    /**
     * 动态 ID
     */
    private Long momentId;

}

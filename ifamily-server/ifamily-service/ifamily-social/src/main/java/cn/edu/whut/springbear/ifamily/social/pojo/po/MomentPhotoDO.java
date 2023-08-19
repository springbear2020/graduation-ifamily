package cn.edu.whut.springbear.ifamily.social.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 17:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("moment_photo")
public class MomentPhotoDO extends AbstractBaseDO {

    private static final long serialVersionUID = 7917477462836538027L;

    /**
     * 动态图片地址
     */
    private String url;

    /**
     * 动态 ID
     */
    private Long momentId;

    /**
     * 上传者用户 ID
     */
    private Long uploaderUserId;

}

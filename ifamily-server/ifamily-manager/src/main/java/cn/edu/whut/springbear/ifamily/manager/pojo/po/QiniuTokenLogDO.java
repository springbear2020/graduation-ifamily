package cn.edu.whut.springbear.ifamily.manager.pojo.po;

import cn.edu.whut.springbear.ifamily.model.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Spring-_-Bear
 * @since 23/03/27 16:07
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("qiniu_token_log")
public class QiniuTokenLogDO extends AbstractBaseDO {

    private static final long serialVersionUID = 8447035104243285309L;

    /**
     * 存储桶名称
     */
    private String bucket;

    /**
     * 文件名
     */
    private String fileKey;

    /**
     * CDN 域名
     */
    private String cdn;

    /**
     * 令牌
     */
    private String token;

    /**
     * 获取状态：[0]成功 [1]失败
     */
    private Integer status;

    /**
     * 用户 ID
     */
    private Long userId;

}

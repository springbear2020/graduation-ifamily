package cn.edu.whut.springbear.ifamily.manager.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
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
public class QiniuLogDO extends AbstractBaseDO {

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
     * 是否获取成功：[0]否 [1]是
     */
    @TableField("is_success")
    private Integer success;

    /**
     * 用户 ID
     */
    private Long userId;

}

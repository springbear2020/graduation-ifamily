package cn.edu.whut.springbear.ifamily.manager.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/27 16:07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("qiniu_token_log")
public class QiniuTokenLogDO implements Serializable {

    private static final long serialVersionUID = 8447035104243285309L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date modified;

    /**
     * 是否删除：[0]未删除 [1]已删除
     */
    private Integer isDeleted;

    /**
     * 用户 ID
     */
    private Long userId;

}

package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author Spring-_-Bear
 * @since 23/04/17 15:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("genealogy_photo")
public class PhotoDO extends AbstractBaseDO implements Serializable {

    private static final long serialVersionUID = 1011881546322754139L;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 家族 ID
     */
    private Long genealogyId;

    /**
     * 上传者用户 ID
     */
    private Long uploaderUserId;



}

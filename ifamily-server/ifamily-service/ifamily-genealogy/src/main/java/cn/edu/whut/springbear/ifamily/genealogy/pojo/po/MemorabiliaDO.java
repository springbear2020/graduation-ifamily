package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.common.pojo.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 12:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("genealogy_memorabilia")
public class MemorabiliaDO extends AbstractBaseDO {

    private static final long serialVersionUID = 8139911802559143582L;

    /**
     * 大事标题
     */
    private String title;

    /**
     * 发生年份
     */
    private Date occurredYear;

    /**
     * 大事配图图片地址
     */
    private String cover;

    /**
     * 内容
     */
    private String content;

    /**
     * 家族 ID
     */
    private Long genealogyId;

    /**
     * 发布者用户 ID
     */
    private Long publisherUserId;

}

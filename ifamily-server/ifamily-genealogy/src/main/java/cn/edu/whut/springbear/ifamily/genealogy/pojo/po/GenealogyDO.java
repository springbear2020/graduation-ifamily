package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.model.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 家族信息
 *
 * @author Spring-_-Bear
 * @since 23/03/28 15:51
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("genealogy")
public class GenealogyDO extends AbstractBaseDO {

    private static final long serialVersionUID = 56242153162165395L;

    /**
     * 封面图片地址
     */
    private String cover;

    /**
     * 家族名称
     */
    private String title;

    /**
     * 家族姓氏
     */
    private String surname;

    /**
     * 家族地址
     */
    private String address;

    /**
     * 祖籍地址
     */
    private String ancestryAddress;

    /**
     * 家族简介
     */
    private String introduction;

    /**
     * 字辈歌
     */
    private String generationSong;

    /**
     * 创建者用户 ID
     */
    private Long creatorUserId;

}
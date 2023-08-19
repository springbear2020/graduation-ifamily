package cn.edu.whut.springbear.ifamily.genealogy.pojo.po;

import cn.edu.whut.springbear.ifamily.model.po.AbstractBaseDO;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 19:26
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("people")
public class PeopleDO extends AbstractBaseDO {

    private static final long serialVersionUID = 460015047129699159L;

    /**
     * 人员肖像图片地址
     */
    private String portrait;

    /**
     * 姓氏
     */
    private String surname;

    /**
     * 名字
     */
    private String name;

    /**
     * 全名：姓氏 + 名字
     */
    private String fullName;

    /**
     * 性别：[0]男 [1]女
     */
    private Integer gender;

    /**
     * 世代
     */
    private Integer generation;

    /**
     * 字辈
     */
    private String generationName;

    /**
     * 世代
     */
    private Integer seniority;

    /**
     * 电话
     */
    private String phone;

    /**
     * 常住地
     */
    private String residence;

    /**
     * 出生日期
     */
    private Date birthdate;

    /**
     * 是否农历出生日期：[0]否 [1]是
     */
    private Integer lunarBirthdate;

    /**
     * 出生地
     */
    private String birthplace;

    /**
     * 逝世日期
     */
    private Date deathDate;

    /**
     * 是否农历逝世日期：[0]否 [1]是
     */
    private Integer lunarDeathDate;

    /**
     * 埋葬地
     */
    private String burialPlace;

    /**
     * 特殊家庭关系备注，如养子、养女、继子、继女等
     */
    private String familyRelationRemark;

    /**
     * 用户 ID
     */
    private Long userId;

    /**
     * 父亲 ID
     */
    private Long fatherId;

    /**
     * 母亲 ID
     */
    private Long motherId;

    /**
     * 家族 ID
     */
    private Long genealogyId;

}

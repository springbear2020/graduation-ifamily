package cn.edu.whut.springbear.ifamily.genealogy.pojo.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/04/01 22:36
 */
@Data
public class PeopleQuery implements Serializable {

    private static final long serialVersionUID = 2234229965237156379L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("人员肖像图片地址")
    private String portrait;

    @ApiModelProperty("姓氏")
    @Pattern(regexp = "^.{1,30}$", message = "请填写人员姓氏, 长度不大于 30")
    private String surname;

    @ApiModelProperty("名字")
    @Pattern(regexp = "^.{1,30}$", message = "请填写人员名字, 长度不大于 30")
    private String name;

    @ApiModelProperty("性别：[0]男 [1]女")
    @Min(value = 0L, message = "性别：[0]男 [1]女")
    @Max(value = 1L, message = "性别：[0]男 [1]女")
    @NotNull
    private Integer gender;

    @ApiModelProperty("世代")
    @Min(value = 1L, message = "世代值不能小于 1")
    @NotNull
    private Integer generation;

    @ApiModelProperty("排行")
    @Min(value = 1L, message = "排行值不能小于 1")
    private Integer seniority;

    @ApiModelProperty("字辈")
    private String generationName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("常住地")
    private String residence;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("出生日期：yyyy-MM-dd")
    private Date birthdate;

    @ApiModelProperty("是否农历出生日期：[0]否 [1]是")
    private Integer lunarBirthdate;

    @ApiModelProperty("出生地")
    private String birthplace;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("逝世日期：yyyy-MM-dd")
    private Date deathDate;

    @ApiModelProperty("是否农历逝世日期：[0]否 [1]是")
    private Integer lunarDeathDate;

    @ApiModelProperty("埋葬地")
    private String burialPlace;

    @ApiModelProperty("特殊家庭关系备注：如养子、养女、继子、继女等")
    private String familyRelationRemark;

    @ApiModelProperty("添加亲人：[1]生父 [2]生母 [3]配偶 [4]子女 [5]同胞")
    private Integer type;

}

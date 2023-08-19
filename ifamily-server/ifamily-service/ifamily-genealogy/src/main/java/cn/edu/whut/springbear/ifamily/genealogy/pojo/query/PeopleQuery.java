package cn.edu.whut.springbear.ifamily.genealogy.pojo.query;

import cn.edu.whut.springbear.ifamily.common.constant.RegExpConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
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
    @Pattern(regexp = RegExpConstants.URL_PATTERN, message = "请上传您的家族人员肖像图片")
    @Size(max = 250, message = "图片地址长度不大于 250")
    private String portrait;

    @ApiModelProperty(value = "姓氏", required = true)
    @Size(max = 30, message = "请填写人员姓氏, 长度不大于 30")
    @NotBlank(message = "人员姓氏值不能为空")
    private String surname;

    @ApiModelProperty(value = "姓名", required = true)
    @Size(max = 30, message = "请填写人员姓名, 长度不大于 30")
    @NotBlank(message = "人员姓名值不能为空")
    private String name;

    @ApiModelProperty(value = "性别：[0]男 [1]女", required = true)
    @Range(max = 1L, message = "性别：[0]男 [1]女")
    @NotNull(message = "人员性别值不能为空")
    private Integer gender;

    @ApiModelProperty(value = "世代", required = true)
    @Min(value = 1L, message = "世代值不能小于 1")
    @NotNull(message = "世代值不能为空")
    private Integer generation;

    @ApiModelProperty(value = "排行", required = true)
    @Min(value = 1L, message = "排行值不能小于 1")
    @NotNull(message = "排行值不能为空")
    private Integer seniority;

    @ApiModelProperty(value = "字辈", required = true)
    @Size(max = 30, message = "请填写人员字辈, 长度不大于 30")
    @NotEmpty(message = "人员字辈不能为空")
    private String generationName;

    @ApiModelProperty("手机号")
    @Pattern(regexp = RegExpConstants.PHONE_PATTERN, message = "手机号格式不正确，请重新输入")
    private String phone;

    @ApiModelProperty("常住地")
    @Size(max = 250, message = "请填写常住地，长度不大于 250")
    private String residence;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("出生日期：yyyy-MM-dd")
    @Past(message = "出生日期必须是一个过去的日期")
    private Date birthdate;

    @ApiModelProperty("是否农历出生日期：[0]否 [1]是")
    @Range(max = 1L, message = "是否农历出生日期：[0]否 [1]是")
    private Integer lunarBirthdate;

    @ApiModelProperty("出生地")
    @Size(max = 250, message = "请填写出生地，长度不大于 250")
    private String birthplace;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("逝世日期：yyyy-MM-dd")
    @Past(message = "逝世日期必须是一个过去的日期")
    private Date deathDate;

    @ApiModelProperty("是否农历逝世日期：[0]否 [1]是")
    @Range(max = 1L, message = "是否农历逝世日期：[0]否 [1]是")
    private Integer lunarDeathDate;

    @ApiModelProperty("埋葬地")
    @Size(max = 250, message = "请填写埋葬地，长度不大于 250")
    private String burialPlace;

    @ApiModelProperty("特殊家庭关系备注：如养子、养女、继子、继女等")
    @Size(max = 250, message = "请填写特殊家庭关系备注，长度不大于 250")
    private String familyRelationRemark;

}

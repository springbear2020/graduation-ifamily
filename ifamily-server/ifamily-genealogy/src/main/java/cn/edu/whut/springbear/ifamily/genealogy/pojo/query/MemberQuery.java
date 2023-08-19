package cn.edu.whut.springbear.ifamily.genealogy.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * @author Spring-_-Bear
 * @since 23/04/04 20:24
 */
@Data
public class MemberQuery implements Serializable {

    private static final long serialVersionUID = -8535086684903836231L;

    @ApiModelProperty("家族 ID")
    private Long genealogyId;

    @ApiModelProperty("家族人员姓名")
    private String name;

    @ApiModelProperty("性别：[0]男 [1]女")
    @Range(max = 1L, message = "性别：[0]男 [1]女")
    private Integer gender;

    @ApiModelProperty("生逝：[true]健在 [false]已逝")
    private Boolean alive;

}

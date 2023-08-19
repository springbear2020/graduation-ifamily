package cn.edu.whut.springbear.ifamily.social.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Spring-_-Bear
 * @since 23/04/30 11:51
 */
@Data
public class MomentCommentQuery implements Serializable {

    private static final long serialVersionUID = 5806365030562388600L;

    @ApiModelProperty(value = "评论内容", required = true)
    @Size(max = 1000, message = "请填写评论内容, 长度不大于 1000")
    @NotBlank(message = "评论内容值不能为空")
    private String content;

    @ApiModelProperty(value = "动态 ID", required = true)
    @NotNull(message = "动态 ID 值不能为空")
    private Long momentId;

    @ApiModelProperty(value = "被回复者用户 ID", required = true)
    @NotNull(message = "被回复者用户 ID 值不能为空")
    private Long targetUserId;

    @ApiModelProperty(value = "父级评论 ID", required = true)
    @NotNull(message = "父级评论 ID 值不能为空")
    private Long parentId;

}

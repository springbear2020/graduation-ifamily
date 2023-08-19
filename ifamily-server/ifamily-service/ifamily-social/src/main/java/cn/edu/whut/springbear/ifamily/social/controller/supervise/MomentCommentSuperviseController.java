package cn.edu.whut.springbear.ifamily.social.controller.supervise;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.social.pojo.query.MomentCommentQuery;
import cn.edu.whut.springbear.ifamily.social.service.MomentCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Spring-_-Bear
 * @since 23/04/30 11:47
 */
@RequiredArgsConstructor
@Api(tags = "动态评论监督接口")
@RestController
@RequestMapping("/social/supervise/moment/comment")
public class MomentCommentSuperviseController {

    private final HttpServletRequest httpServletRequest;
    private final MomentCommentService momentCommentService;

    @ApiOperation("用户评论动态")
    @PostMapping
    public CommonResult<Object> create(@Validated @RequestBody MomentCommentQuery momentCommentQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        Long commentId = this.momentCommentService.create(userDTO.getId(), momentCommentQuery);
        return commentId != null ? CommonResult.success(commentId) : CommonResult.failed("请求评论动态失败");
    }

}

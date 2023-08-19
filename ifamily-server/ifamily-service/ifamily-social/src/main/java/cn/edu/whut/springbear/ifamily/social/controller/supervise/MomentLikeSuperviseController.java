package cn.edu.whut.springbear.ifamily.social.controller.supervise;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.social.service.MomentLikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Spring-_-Bear
 * @since 23/04/29 19:54
 */
@RequiredArgsConstructor
@Api(tags = "动态点赞监督接口")
@RestController
@RequestMapping("/social/supervise/moment/like")
public class MomentLikeSuperviseController {

    private final HttpServletRequest httpServletRequest;
    private final MomentLikeService momentLikeService;

    @ApiOperation("用户点赞动态")
    @PostMapping("/{momentId}")
    public CommonResult<Object> create(@ApiParam("动态 ID") @PathVariable("momentId") Long momentId) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        Long momentLikeId = this.momentLikeService.create(userDTO.getId(), momentId);
        return momentLikeId != null ? CommonResult.success(momentLikeId) : CommonResult.failed("请求点赞动态失败");
    }

    @ApiOperation("用户取消点赞动态")
    @DeleteMapping("/{momentId}")
    public CommonResult<String> remove(@ApiParam("动态 ID") @PathVariable("momentId") Long momentId) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        boolean deleteResult = this.momentLikeService.remove(userDTO.getId(), momentId);
        return deleteResult ? CommonResult.success() : CommonResult.failed("请求取消点赞动态失败");
    }

}

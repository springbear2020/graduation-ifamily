package cn.edu.whut.springbear.ifamily.social.controller.supervise;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.social.pojo.bo.MomentBO;
import cn.edu.whut.springbear.ifamily.social.pojo.bo.MomentDetailsBO;
import cn.edu.whut.springbear.ifamily.social.pojo.query.MomentQuery;
import cn.edu.whut.springbear.ifamily.social.service.MomentService;
import cn.edu.whut.springbear.ifamily.social.service.feign.GenealogyFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 18:00
 */
@RequiredArgsConstructor
@Api(tags = "社交动态监督接口")
@RestController
@RequestMapping("/social/supervise/moment")
public class MomentSuperviseController {

    private final HttpServletRequest httpServletRequest;
    private final MomentService momentService;
    private final GenealogyFeignService genealogyFeignService;

    @ApiOperation("发布用户动态")
    @PostMapping
    public CommonResult<String> create(@Validated @RequestBody MomentQuery momentQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        boolean saveResult = this.momentService.create(userDTO.getId(), momentQuery);
        return saveResult ? CommonResult.success() : CommonResult.failed("请求发布动态失败");
    }

    @ApiOperation("查询用户动态分页数据")
    @GetMapping
    public CommonResult<Object> page(@Validated PageQuery pageQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        List<MomentDetailsBO> momentList = this.momentService.page(userDTO.getId(), pageQuery);
        return momentList == null || momentList.isEmpty() ? CommonResult.failed("用户动态无数据") : CommonResult.success(momentList);
    }

    @ApiOperation("删除用户动态")
    @DeleteMapping("/{momentId}")
    public CommonResult<Object> remove(@ApiParam("动态 ID") @PathVariable("momentId") Long momentId) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        boolean deleteResult = this.momentService.remove(userDTO.getId(), momentId);
        return deleteResult ? CommonResult.success() : CommonResult.failed("请求删除动态失败");
    }

    @ApiOperation("查询用户动态")
    @GetMapping("/{momentId}")
    public CommonResult<Object> get(@ApiParam("动态 ID") @PathVariable("momentId") Long momentId) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        MomentBO momentBO = this.momentService.get(userDTO.getId(), momentId);
        return momentBO == null ? CommonResult.failed("用户动态无数据") : CommonResult.success(momentBO);
    }

    @ApiOperation("更新用户动态")
    @PutMapping
    public CommonResult<String> edit(@Validated @RequestBody MomentQuery momentQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        boolean updateResult = this.momentService.edit(userDTO.getId(), momentQuery);
        return updateResult ? CommonResult.success() : CommonResult.failed("请求更新动态失败");
    }

    @ApiOperation("查询用户默认家族成员动态分页数据")
    @GetMapping("/genealogy")
    public CommonResult<Object> pageData(@Validated PageQuery pageQuery) {
        Long userId = WebUtils.parseGeneralUser(httpServletRequest).getId();
        Long genealogyId = this.genealogyFeignService.getUserDefaultGenealogyId(userId);
        List<MomentDetailsBO> momentList = this.momentService.pageByGenealogyId(userId, genealogyId, pageQuery);
        return momentList == null || momentList.isEmpty() ? CommonResult.failed("默认家族成员动态无数据") : CommonResult.success(momentList);
    }

}

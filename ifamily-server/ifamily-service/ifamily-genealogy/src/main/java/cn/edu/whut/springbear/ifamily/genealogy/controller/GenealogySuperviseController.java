package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.GenealogyDetailsBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyService;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 15:59
 */
@AllArgsConstructor
@Api(tags = "家族信息监督接口")
@RestController
@RequestMapping("/genealogy/supervise")
public class GenealogySuperviseController {

    private final HttpServletRequest httpServletRequest;
    private final GenealogyService genealogyService;
    private final UserGenealogyService userGenealogyService;

    @ApiOperation("用户创建家族")
    @PostMapping
    public CommonResult<String> create(@Validated @RequestBody GenealogyQuery genealogyQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        boolean saveResult = this.genealogyService.create(genealogyQuery, userDTO.getId());
        return saveResult ? CommonResult.success() : CommonResult.failed("请求创建家族失败");
    }

    @ApiOperation("查询用户家族列表详细信息")
    @GetMapping
    public CommonResult<Object> list() {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        List<GenealogyDetailsBO> genealogiesOfUser = this.genealogyService.listWithDetails(userDTO.getId());
        return genealogiesOfUser == null || genealogiesOfUser.isEmpty() ? CommonResult.failed("家族列表无数据") : CommonResult.success(genealogiesOfUser);
    }

    @ApiOperation("设置用户默认家族")
    @PutMapping("/{genealogyId}")
    public CommonResult<String> setDefault(@ApiParam("家族 ID") @PathVariable("genealogyId") Long genealogyId) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        boolean updateResult = this.userGenealogyService.setDefault(userDTO.getId(), genealogyId);
        return updateResult ? CommonResult.success() : CommonResult.failed("请求设置默认家族失败");
    }

}

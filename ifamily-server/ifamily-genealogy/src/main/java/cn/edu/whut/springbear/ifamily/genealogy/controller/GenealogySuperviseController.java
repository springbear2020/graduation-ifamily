package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.SystemMessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.constant.GenealogyMessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.RevisionBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.VisitorBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.GenealogyDetailsBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.genealogy.service.*;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 15:59
 */
@Api(tags = "家族信息监督管理接口")
@RestController
@RequestMapping("/supervise/genealogy")
public class GenealogySuperviseController {

    @Autowired
    private GenealogyService genealogyService;
    @Autowired
    private UserGenealogyService userGenealogyService;
    @Autowired
    private SecurityUserService securityUserService;
    @Autowired
    private VisitorLogService visitorLogService;
    @Autowired
    private RevisionLogService revisionLogService;

    @ApiOperation("新增家族")
    @PostMapping
    public CommonResult<String> create(@Validated @RequestBody GenealogyQuery genealogyQuery) {
        UserDO currentUser = securityUserService.getCurrentUser();
        boolean saveResult = this.genealogyService.create(genealogyQuery, currentUser.getId());
        return saveResult ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("查询用户所有家族和信息概况")
    @GetMapping
    public CommonResult<Object> list() {
        UserDO currentUser = securityUserService.getCurrentUser();
        List<GenealogyDetailsBO> genealogiesOfUser = this.genealogyService.listWithDetailsOfUser(currentUser.getId());
        return genealogiesOfUser == null || genealogiesOfUser.isEmpty() ? CommonResult.failed("家族列表无数据") : CommonResult.success(genealogiesOfUser);
    }

    @ApiOperation("设置用户默认家族")
    @PutMapping("/{genealogyId}")
    public CommonResult<String> setDefault(@ApiParam("家族 ID") @PathVariable("genealogyId") Long genealogyId) {
        UserDO currentUser = securityUserService.getCurrentUser();
        boolean updateResult = this.userGenealogyService.setDefault(currentUser.getId(), genealogyId);
        return updateResult ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("更新用户默认家族资料")
    @PutMapping
    public CommonResult<String> edit(@Validated @RequestBody GenealogyQuery genealogyQuery) {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed(GenealogyMessageConstants.DEFAULT_NOT_EXISTS);
        }

        // 更新用户默认家族信息
        genealogyQuery.setId(defaultGenealogy.getGenealogyId());
        boolean updateResult = this.genealogyService.edit(genealogyQuery);
        return updateResult ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("查看默认家族修谱日志")
    @GetMapping("/revision")
    public CommonResult<Object> revisionLog(@Validated PageQuery pageQuery) {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed(GenealogyMessageConstants.DEFAULT_NOT_EXISTS);
        }

        List<RevisionBO> pageData = this.revisionLogService.page(pageQuery, defaultGenealogy.getGenealogyId());
        return pageData == null || pageData.isEmpty() ? CommonResult.failed("家族修谱日志无数据") : CommonResult.success(pageData);
    }

    @ApiOperation("查看默认家族访问记录")
    @GetMapping("/visitor")
    public CommonResult<Object> visitorLog(@Validated PageQuery pageQuery) {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed(GenealogyMessageConstants.DEFAULT_NOT_EXISTS);
        }

        List<VisitorBO> pageData = this.visitorLogService.page(pageQuery, defaultGenealogy.getGenealogyId());
        return pageData == null || pageData.isEmpty() ? CommonResult.failed("家族访问记录无数据") : CommonResult.success(pageData);
    }

}

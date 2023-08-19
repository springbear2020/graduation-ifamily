package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.SystemMessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyUserDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.GenealogyVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyService;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyUserService;
import cn.edu.whut.springbear.ifamily.genealogy.service.SecurityUserService;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    private GenealogyUserService genealogyUserService;
    @Autowired
    private SecurityUserService securityUserService;

    @ApiOperation("新增家族")
    @PostMapping
    public CommonResult<String> saveGenealogy(@Validated @RequestBody GenealogyQuery genealogyQuery) {
        UserDO currentUser = securityUserService.getCurrentUser();
        boolean saveResult = this.genealogyService.create(genealogyQuery, currentUser.getId());
        return saveResult ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("查询用户所有家族和信息概况")
    @GetMapping
    public CommonResult<Object> listGenealogiesOfUser() {
        UserDO currentUser = securityUserService.getCurrentUser();
        List<GenealogyVO> genealogiesOfUser = this.genealogyService.listGenealogiesWithProfileOfUser(currentUser.getId());
        return genealogiesOfUser == null || genealogiesOfUser.isEmpty() ? CommonResult.failed("家族列表信息无数据") : CommonResult.success(genealogiesOfUser);
    }

    @ApiOperation("设置用户默认家族")
    @PutMapping("/{genealogyId}")
    public CommonResult<String> setDefaultGenealogy(@PathVariable("genealogyId") Long genealogyId) {
        UserDO currentUser = securityUserService.getCurrentUser();
        boolean result = this.genealogyUserService.setDefaultGenealogyForUser(currentUser.getId(), genealogyId);
        return result ? CommonResult.success() : CommonResult.failed("设置默认家族失败");
    }

    @ApiOperation("更新用户默认家族资料")
    @PutMapping
    public CommonResult<String> editDefaultGenealogy(@Validated @RequestBody GenealogyQuery genealogyQuery) {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        GenealogyUserDO defaultGenealogy = this.genealogyUserService.getDefaultGenealogyOfUser(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed("默认家族信息无数据");
        }

        // 更新用户默认家族信息
        genealogyQuery.setId(defaultGenealogy.getGenealogyId());
        boolean result = this.genealogyService.editGenealogy(genealogyQuery);
        return result ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

}

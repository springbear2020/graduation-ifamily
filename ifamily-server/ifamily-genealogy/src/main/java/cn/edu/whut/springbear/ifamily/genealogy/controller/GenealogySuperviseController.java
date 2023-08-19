package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.SystemMessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.GenealogyMemberVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyService;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyUserService;
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

    @ApiOperation("新增家族")
    @PostMapping
    public CommonResult<String> saveGenealogy(@Validated @RequestBody GenealogyQuery genealogyQuery) {
        boolean saveResult = this.genealogyService.create(genealogyQuery);
        return saveResult ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("查询用户所有家族")
    @GetMapping("/list")
    public CommonResult<Object> listGenealogiesOfUser() {
        List<GenealogyMemberVO> genealogiesOfUser = this.genealogyService.listGenealogiesOfUser();
        return genealogiesOfUser == null || genealogiesOfUser.isEmpty() ? CommonResult.failed("不存在家族信息") : CommonResult.success(genealogiesOfUser);
    }

    @ApiOperation("设置用户默认家族")
    @PutMapping("/{genealogyId}")
    public CommonResult<String> setDefaultGenealogy(@PathVariable("genealogyId") Long genealogyId) {
        boolean result = this.genealogyUserService.setDefaultGenealogyForUser(genealogyId);
        return result ? CommonResult.success() : CommonResult.failed("设置默认家族失败");
    }

}

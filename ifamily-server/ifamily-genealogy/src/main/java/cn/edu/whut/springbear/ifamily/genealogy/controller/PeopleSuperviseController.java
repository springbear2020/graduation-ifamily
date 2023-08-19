package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.SystemMessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.PeopleDetailsBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyUserDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.PeopleQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyUserService;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import cn.edu.whut.springbear.ifamily.genealogy.service.SecurityUserService;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Spring-_-Bear
 * @since 23/03/31 10:45
 */
@Api(tags = "家族人员信息监督管理接口")
@RestController
@RequestMapping("/supervise/genealogy/people")
public class PeopleSuperviseController {

    @Autowired
    private SecurityUserService securityUserService;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private GenealogyUserService genealogyUserService;

    @ApiOperation("查询当前用户在默认家族中的人员信息")
    @GetMapping("/current")
    public CommonResult<Object> currentUserGenealogyPeople() {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        GenealogyUserDO defaultGenealogy = this.genealogyUserService.getDefaultGenealogyOfUser(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed("默认家族信息无数据");
        }

        PeopleVO userPeople = this.peopleService.getByUserGenealogyId(currentUser.getId(), defaultGenealogy.getGenealogyId());
        return userPeople == null ? CommonResult.failed("家族中不存在您的资料") : CommonResult.success(userPeople);
    }

    @ApiOperation("查询家族人员详细信息")
    @GetMapping("/details/{peopleId}")
    public CommonResult<Object> getGenealogyPeopleProfileDetails(@PathVariable("peopleId") Long peopleId) {
        PeopleDetailsBO peopleDetailsBO = this.peopleService.getGenealogyPeopleProfileDetails(peopleId);
        return peopleDetailsBO == null ? CommonResult.failed("家族人员信息无数据") : CommonResult.success(peopleDetailsBO);
    }

    @ApiOperation("查询家族人员")
    @GetMapping("/{peopleId}")
    public CommonResult<Object> getGenealogyPeople(@PathVariable("peopleId") Long peopleId) {
        PeopleVO peopleVO = this.peopleService.getPeopleById(peopleId);
        return peopleVO == null ? CommonResult.failed("家族人员信息无数据") : CommonResult.success(peopleVO);
    }

    @ApiOperation("更新家族人员")
    @PutMapping
    public CommonResult<String> updateGenealogyPeople(@Validated @RequestBody PeopleQuery peopleQuery) {
        // 查询人员信息是否存在
        PeopleVO peopleVO = this.peopleService.getPeopleById(peopleQuery.getId());
        if (peopleVO == null) {
            return CommonResult.failed("家族人员信息无数据");
        }

        boolean result = this.peopleService.updatePeopleById(peopleQuery);
        return result ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("移除家族成员")
    @DeleteMapping
    public CommonResult<String> removeGenealogyPeople(@RequestParam("peopleId") Long peopleId) {
        // 查询人员信息是否存在
        PeopleVO peopleVO = this.peopleService.getPeopleById(peopleId);
        if (peopleVO == null) {
            return CommonResult.failed("家族人员信息无数据");
        }

        boolean result = this.peopleService.removeById(peopleId);
        return result ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("添加亲人：[1]生父 [2]生母 [3]配偶 [4]子女 [5]同胞")
    @PostMapping
    public CommonResult<String> addPeopleRelatives(@RequestBody PeopleQuery peopleQuery) {
        // 查询人员信息是否存在
        final Long mePeopleId = peopleQuery.getId();
        PeopleVO peopleVO = this.peopleService.getPeopleById(mePeopleId);
        if (peopleVO == null) {
            return CommonResult.failed("家族人员信息无数据");
        }

        final int relativeType = peopleQuery.getType();
        boolean result;
        switch (relativeType) {
            // 添加生父
            case 1:
                result = this.peopleService.savePeopleFather(mePeopleId, peopleQuery);
                break;
            // 添加生母
            case 2:
                result = this.peopleService.savePeopleMother(mePeopleId, peopleQuery);
                break;
            // 添加配偶
            case 3:
                result = this.peopleService.savePeopleWife(mePeopleId, peopleQuery);
                break;
            // 添加孩子
            case 4:
                result = this.peopleService.savePeopleChild(mePeopleId, peopleQuery);
                break;
            // 添加同胞
            case 5:
                result = this.peopleService.savePeopleCompatriot(mePeopleId, peopleQuery);
                break;
            default:
                return CommonResult.failed("添加亲人：[1]生父 [2]生母 [3]配偶 [4]子女 [5]同胞");
        }

        return result ? CommonResult.success() : CommonResult.failed("添加亲人失败，请稍后重试");
    }

}

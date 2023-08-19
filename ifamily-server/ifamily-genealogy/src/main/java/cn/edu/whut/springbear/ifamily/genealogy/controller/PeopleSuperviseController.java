package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.SystemMessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.constant.GenealogyMessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.PeopleDetailsBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.PeopleQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import cn.edu.whut.springbear.ifamily.genealogy.service.SecurityUserService;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    private UserGenealogyService userGenealogyService;

    @ApiOperation("查询当前用户在默认家族中的人员信息")
    @GetMapping("/current")
    public CommonResult<Object> getCurrent() {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed(GenealogyMessageConstants.DEFAULT_NOT_EXISTS);
        }

        PeopleVO userPeople = this.peopleService.getByUserGenealogyId(currentUser.getId(), defaultGenealogy.getGenealogyId());
        return userPeople.getId() == null ? CommonResult.failed("家族中不存在您的资料") : CommonResult.success(userPeople);
    }

    @ApiOperation("保存当前用户在默认家族中的人员信息")
    @PostMapping("/current")
    public CommonResult<String> saveCurrent(@RequestBody PeopleQuery peopleQuery) {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed(GenealogyMessageConstants.DEFAULT_NOT_EXISTS);
        }

        boolean saveResult = this.peopleService.create(peopleQuery, currentUser.getId(), defaultGenealogy.getGenealogyId());
        return saveResult ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("查询家族人员详细信息")
    @GetMapping("/details")
    public CommonResult<Object> getPeopleDetails(@ApiParam("家族人员 ID") @RequestParam("peopleId") Long peopleId) {
        PeopleDetailsBO peopleDetailsBO = this.peopleService.getWithDetails(peopleId);
        return peopleDetailsBO == null ? CommonResult.failed(GenealogyMessageConstants.PEOPLE_NOT_EXISTS) : CommonResult.success(peopleDetailsBO);
    }

    @ApiOperation("查询家族人员")
    @GetMapping
    public CommonResult<Object> get(@ApiParam("家族人员 ID") @RequestParam("peopleId") Long peopleId) {
        PeopleVO peopleVO = this.peopleService.get(peopleId);
        return peopleVO == null ? CommonResult.failed(GenealogyMessageConstants.PEOPLE_NOT_EXISTS) : CommonResult.success(peopleVO);
    }

    @ApiOperation("更新家族人员")
    @PutMapping
    public CommonResult<String> edit(@Validated @RequestBody PeopleQuery peopleQuery) {
        // 查询人员信息是否存在
        PeopleVO peopleVO = this.peopleService.get(peopleQuery.getId());
        if (peopleVO == null) {
            return CommonResult.failed(GenealogyMessageConstants.PEOPLE_NOT_EXISTS);
        }

        boolean updateResult = this.peopleService.edit(peopleQuery);
        return updateResult ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("移除家族成员")
    @DeleteMapping
    public CommonResult<String> remove(@ApiParam("家族人员 ID") @RequestParam("peopleId") Long peopleId) {
        // 查询人员信息是否存在
        PeopleVO peopleVO = this.peopleService.get(peopleId);
        if (peopleVO == null) {
            return CommonResult.failed(GenealogyMessageConstants.PEOPLE_NOT_EXISTS);
        }

        boolean deleteResult = this.peopleService.remove(peopleId);
        return deleteResult ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("添加亲人：[1]生父 [2]生母 [3]配偶 [4]子女 [5]同胞")
    @PostMapping
    public CommonResult<String> create(@RequestBody PeopleQuery peopleQuery) {
        // 查询人员信息是否存在
        final Long mePeopleId = peopleQuery.getId();
        PeopleVO peopleVO = this.peopleService.get(mePeopleId);
        if (peopleVO == null) {
            return CommonResult.failed(GenealogyMessageConstants.PEOPLE_NOT_EXISTS);
        }

        final int relativeType = peopleQuery.getType();
        boolean saveResult;
        switch (relativeType) {
            // 添加生父
            case 1:
                saveResult = this.peopleService.savePeopleFather(mePeopleId, peopleQuery);
                break;
            // 添加生母
            case 2:
                saveResult = this.peopleService.savePeopleMother(mePeopleId, peopleQuery);
                break;
            // 添加配偶
            case 3:
                saveResult = this.peopleService.savePeopleWife(mePeopleId, peopleQuery);
                break;
            // 添加孩子
            case 4:
                saveResult = this.peopleService.savePeopleChild(mePeopleId, peopleQuery);
                break;
            // 添加同胞
            case 5:
                saveResult = this.peopleService.savePeopleCompatriot(mePeopleId, peopleQuery);
                break;
            default:
                return CommonResult.failed("添加亲人：[1]生父 [2]生母 [3]配偶 [4]子女 [5]同胞");
        }

        return saveResult ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

}

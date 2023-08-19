package cn.edu.whut.springbear.ifamily.genealogy.controller.admin;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.constant.MessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.PeopleQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Spring-_-Bear
 * @since 23/04/15 21:07
 */
@AllArgsConstructor
@Api(tags = "家族人员管理接口")
@RestController
@RequestMapping("/genealogy/people/admin")
public class PeopleAdminController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final PeopleService peopleService;

    @ApiOperation("添加用户默认家族亲人")
    @PostMapping
    public CommonResult<String> create(
            @Validated @RequestBody PeopleQuery peopleQuery,
            @ApiParam("亲人类型：[1]生父 [2]生母 [3]配偶 [4]子女 [5]同胞") @RequestParam("relativeType") Integer relativeType) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        final Long userId = userDTO.getId();
        final Long genealogyId = this.userGenealogyService.getDefault(userDTO.getId()).getGenealogyId();
        final Long mePeopleId = peopleQuery.getId();

        boolean saveResult;
        switch (relativeType) {
            // 添加生父
            case 1:
                saveResult = this.peopleService.saveGenealogyPeopleFather(genealogyId, mePeopleId, peopleQuery, userId);
                break;
            // 添加生母
            case 2:
                saveResult = this.peopleService.saveGenealogyPeopleMother(genealogyId, mePeopleId, peopleQuery, userId);
                break;
            // 添加配偶
            case 3:
                saveResult = this.peopleService.saveGenealogyPeopleWife(genealogyId, mePeopleId, peopleQuery, userId);
                break;
            // 添加孩子
            case 4:
                saveResult = this.peopleService.saveGenealogyPeopleChild(genealogyId, mePeopleId, peopleQuery, userId);
                break;
            // 添加同胞
            case 5:
                saveResult = this.peopleService.saveGenealogyPeopleCompatriot(genealogyId, mePeopleId, peopleQuery, userId);
                break;
            default:
                return CommonResult.preconditionFailed("亲人类型：[1]生父 [2]生母 [3]配偶 [4]子女 [5]同胞");
        }

        return saveResult ? CommonResult.success() : CommonResult.failed("请求添加亲人失败");
    }

    @ApiOperation("移除用户默认家族人员")
    @DeleteMapping
    public CommonResult<String> remove(@ApiParam("家族人员 ID") @RequestParam("peopleId") Long peopleId) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        boolean deleteResult = this.peopleService.removeGenealogyPeople(defaultGenealogy.getGenealogyId(), peopleId, userDTO.getId());
        return deleteResult ? CommonResult.success() : CommonResult.failed("请求删除家族人员失败");
    }

    @ApiOperation("更新用户默认家族人员")
    @PutMapping
    public CommonResult<String> edit(@Validated @RequestBody PeopleQuery peopleQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        boolean updateResult = this.peopleService.editGenealogyPeople(defaultGenealogy.getGenealogyId(), peopleQuery, userDTO.getId());
        return updateResult ? CommonResult.success() : CommonResult.failed("请求更新人员信息失败");
    }

    @ApiOperation("查询用户默认家族人员")
    @GetMapping
    public CommonResult<Object> get(@ApiParam("家族人员 ID") @RequestParam("peopleId") Long peopleId) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        PeopleVO peopleVO = this.peopleService.getGenealogyPeople(defaultGenealogy.getGenealogyId(), peopleId);
        return peopleVO == null ? CommonResult.failed(MessageConstants.PEOPLE_INFO_NO_DATA) : CommonResult.success(peopleVO);
    }

}

package cn.edu.whut.springbear.ifamily.genealogy.controller.supervise;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.constant.MessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.MemberTreeNodeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.PeopleDetailsBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.MemberQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.PeopleQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.MemberService;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/03/31 10:45
 */
@AllArgsConstructor
@Api(tags = "家族人员监督接口")
@RestController
@RequestMapping("/genealogy/supervise/people")
public class PeopleSuperviseController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final PeopleService peopleService;
    private final MemberService memberService;

    @ApiOperation("保存当前用户在默认家族中的人员信息")
    @PostMapping("/current")
    public CommonResult<String> saveCurrentUserPeople(@Validated @RequestBody PeopleQuery peopleQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        boolean saveResult = this.peopleService.createUserPeople(peopleQuery, userDTO.getId(), defaultGenealogy.getGenealogyId());
        return saveResult ? CommonResult.success() : CommonResult.failed("请求保存您的默认家族人员资料失败");
    }

    @ApiOperation("查询当前用户在默认家族中的人员信息")
    @GetMapping("/current")
    public CommonResult<Object> getCurrentUserPeople() {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        PeopleVO userPeople = this.peopleService.getUserPeople(userDTO.getId(), defaultGenealogy.getGenealogyId());
        return userPeople == null ? CommonResult.failed("您的默认家族资料无数据") : CommonResult.success(userPeople);
    }

    @ApiOperation("查询用户默认家族人员")
    @GetMapping("/{peopleId}")
    public CommonResult<Object> get(@ApiParam("家族人员 ID") @PathVariable("peopleId") Long peopleId) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        PeopleVO peopleVO = this.peopleService.getGenealogyPeople(defaultGenealogy.getGenealogyId(), peopleId);
        return peopleVO == null ? CommonResult.failed(MessageConstants.PEOPLE_INFO_NO_DATA) : CommonResult.success(peopleVO);
    }

    @ApiOperation("查询用户默认家族人员详细信息")
    @GetMapping("/details")
    public CommonResult<Object> getPeopleDetails(@ApiParam("家族人员 ID") @RequestParam("peopleId") Long peopleId) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        PeopleDetailsBO peopleDetailsBO = this.peopleService.getPeopleDetails(defaultGenealogy.getGenealogyId(), peopleId, userDTO.getId());
        return peopleDetailsBO == null ? CommonResult.failed(MessageConstants.PEOPLE_INFO_NO_DATA) : CommonResult.success(peopleDetailsBO);
    }

    @ApiOperation("查询用户默认家族成员树谱列表")
    @GetMapping("/tree")
    public CommonResult<Object> memberTree() {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        MemberTreeNodeBO memberTreeNodeBO = this.memberService.memberTree(defaultGenealogy.getGenealogyId());
        return memberTreeNodeBO == null ? CommonResult.failed("默认家族成员树谱无数据") : CommonResult.success(memberTreeNodeBO);
    }

    @ApiOperation("查询用户默认家族世代成员列表")
    @GetMapping("/generation")
    public CommonResult<Object> generationMembers(@Validated MemberQuery memberQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        Map<String, Object> result = this.memberService.listGenerationMembers(memberQuery, defaultGenealogy.getGenealogyId());
        return result == null || result.isEmpty() ? CommonResult.failed("默认家族世代成员列表无数据") : CommonResult.success(result);
    }

    @ApiOperation("根据姓名查询默认家族成员列表")
    @GetMapping("/search")
    public CommonResult<Object> listMembersByName(@ApiParam("家族人员姓名") @NotEmpty(message = "家族人员姓名不能为空") @RequestParam("name") String name) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        List<PeopleCardVO> peopleList = this.memberService.listMembersByName(name, defaultGenealogy.getGenealogyId());
        return peopleList == null || peopleList.isEmpty() ? CommonResult.failed(MessageConstants.PEOPLE_INFO_NO_DATA) : CommonResult.success(peopleList);
    }

}

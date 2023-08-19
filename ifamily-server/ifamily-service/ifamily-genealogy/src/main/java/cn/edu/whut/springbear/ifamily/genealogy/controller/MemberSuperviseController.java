package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.constant.GenealogyMessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.MemberTreeNodeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.MemberQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import cn.edu.whut.springbear.ifamily.genealogy.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/04/02 19:56
 */
@AllArgsConstructor
@Api(tags = "家族成员监督接口")
@RestController
@RequestMapping("/genealogy/member/supervise")
public class MemberSuperviseController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final MemberService memberService;

    @ApiOperation("查询用户默认家族成员树谱列表")
    @GetMapping("/tree")
    public CommonResult<Object> memberTree() {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        MemberTreeNodeBO memberTreeNodeBO = this.memberService.memberTree(defaultGenealogy.getGenealogyId());
        return memberTreeNodeBO == null ? CommonResult.failed("家族成员树谱无数据") : CommonResult.success(memberTreeNodeBO);
    }

    @ApiOperation("查询用户默认家族成员世代列表")
    @GetMapping("/generation")
    public CommonResult<Object> generationMembers(@Validated MemberQuery memberQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        memberQuery.setGenealogyId(defaultGenealogy.getGenealogyId());
        Map<String, Object> result = this.memberService.listGenerationMembers(memberQuery);
        return result == null || result.isEmpty() ? CommonResult.failed("家族成员世代列表无数据") : CommonResult.success(result);
    }

    @ApiOperation("根据姓名查询默认家族成员列表")
    @GetMapping("/search")
    public CommonResult<Object> listMembersByName(@ApiParam("家族人员姓名") @RequestParam("name") String name) {
        if (name == null || name.isEmpty()) {
            return CommonResult.failed("家族人员姓名不能为空");
        }

        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        List<PeopleCardVO> peopleList = this.memberService.listMembersByName(name, defaultGenealogy.getGenealogyId());
        return peopleList == null || peopleList.isEmpty() ? CommonResult.failed(GenealogyMessageConstants.PEOPLE_NOT_EXISTS) : CommonResult.success(peopleList);
    }

}

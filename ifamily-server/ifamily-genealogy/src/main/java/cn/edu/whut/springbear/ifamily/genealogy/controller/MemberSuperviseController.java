package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.genealogy.constant.GenealogyMessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.MemberTreeNodeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.MemberQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import cn.edu.whut.springbear.ifamily.genealogy.service.MemberService;
import cn.edu.whut.springbear.ifamily.genealogy.service.SecurityUserService;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/02 19:56
 */
@Api(tags = "家族成员信息监督管理接口")
@RestController
@RequestMapping("/supervise/genealogy/member")
public class MemberSuperviseController {

    @Autowired
    private SecurityUserService securityUserService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private UserGenealogyService userGenealogyService;

    @ApiOperation("查询用户默认家族成员世代分组列表")
    @GetMapping("/generation")
    public CommonResult<Object> memberList(@Validated MemberQuery memberQuery) {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed(GenealogyMessageConstants.DEFAULT_NOT_EXISTS);
        }

        memberQuery.setGenealogyId(defaultGenealogy.getGenealogyId());
        Object o = this.memberService.listGenerationMembers(memberQuery);
        return o == null ? CommonResult.failed("家族成员世代列表无数据") : CommonResult.success(o);
    }

    @ApiOperation("查询用户默认家族成员树谱列表")
    @GetMapping("/tree")
    public CommonResult<Object> memberTree() {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed(GenealogyMessageConstants.DEFAULT_NOT_EXISTS);
        }

        MemberTreeNodeBO memberTreeNodeBO = this.memberService.memberTree(defaultGenealogy.getGenealogyId());
        return memberTreeNodeBO == null ? CommonResult.failed("家族成员树谱无数据") : CommonResult.success(memberTreeNodeBO);
    }

    @ApiOperation("根据姓名模糊查询默认家族成员列表")
    @GetMapping("/search")
    public CommonResult<Object> listMembersByName(@ApiParam("家族人员姓名") @RequestParam("name") String name) {
        if (name == null || name.isEmpty()) {
            return CommonResult.failed("家族人员姓名不能为空");
        }

        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed(GenealogyMessageConstants.DEFAULT_NOT_EXISTS);
        }

        List<PeopleCardVO> peopleList = this.memberService.listMembersByName(name, defaultGenealogy.getGenealogyId());
        return peopleList == null || peopleList.isEmpty() ? CommonResult.failed(GenealogyMessageConstants.PEOPLE_NOT_EXISTS) : CommonResult.success(peopleList);
    }

}

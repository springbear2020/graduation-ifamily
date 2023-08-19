package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.MemberTreeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyUserDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleCardVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyUserService;
import cn.edu.whut.springbear.ifamily.genealogy.service.MemberService;
import cn.edu.whut.springbear.ifamily.genealogy.service.SecurityUserService;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    private GenealogyUserService genealogyUserService;

    @ApiOperation("查询用户默认家族成员世代分组列表")
    @GetMapping("/generation")
    public CommonResult<Object> groupMembersOfGenealogy() {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        GenealogyUserDO defaultGenealogy = this.genealogyUserService.getDefaultGenealogyOfUser(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed("默认家族信息无数据");
        }

        Object o = this.memberService.listGenerationMembersOfGenealogy(defaultGenealogy.getGenealogyId());
        return o == null ? CommonResult.failed("家族成员无数据") : CommonResult.success(o);
    }

    @ApiOperation("查询用户默认家族成员树谱列表")
    @GetMapping("/tree")
    public CommonResult<Object> memberTreeOfGenealogy() {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        GenealogyUserDO defaultGenealogy = this.genealogyUserService.getDefaultGenealogyOfUser(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed("默认家族信息无数据");
        }

        MemberTreeBO memberTreeBO = this.memberService.listGenealogyMemberTree(defaultGenealogy.getGenealogyId());
        return memberTreeBO == null ? CommonResult.failed("家族成员无数据") : CommonResult.success(memberTreeBO);
    }

    @ApiOperation("根据姓名模糊查询默认家族成员列表")
    @GetMapping("/search")
    public CommonResult<Object> listPeopleByName(@RequestParam("name") String name) {
        if (name == null || name.isEmpty()) {
            return CommonResult.failed("家族人员姓名不能为空");
        }

        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        GenealogyUserDO defaultGenealogy = this.genealogyUserService.getDefaultGenealogyOfUser(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed("默认家族信息无数据");
        }

        List<PeopleCardVO> peopleList = this.memberService.listPeopleByNameOfGenealogy(name, defaultGenealogy.getId());
        return peopleList == null || peopleList.isEmpty() ? CommonResult.failed("家族成员无数据") : CommonResult.success(peopleList);
    }

}

package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.GenealogyMemberTreeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.GenealogyUserDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.PeopleVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyUserService;
import cn.edu.whut.springbear.ifamily.genealogy.service.PeopleService;
import cn.edu.whut.springbear.ifamily.genealogy.service.SecurityUserService;
import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("查询用户默认家族人员世代分组列表")
    @GetMapping("/generation")
    public CommonResult<Object> groupMembersOfGenealogy() {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        GenealogyUserDO defaultGenealogy = this.genealogyUserService.getDefaultGenealogyOfUser(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed("默认家族信息无数据");
        }

        Object o = this.peopleService.listGenerationMembersOfGenealogy(defaultGenealogy.getGenealogyId());
        return o == null ? CommonResult.failed("家族人员无数据") : CommonResult.success(o);
    }

    @ApiOperation("查询用户默认家族人员树谱列表")
    @GetMapping("/tree")
    public CommonResult<Object> memberTreeOfGenealogy() {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        GenealogyUserDO defaultGenealogy = this.genealogyUserService.getDefaultGenealogyOfUser(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed("默认家族信息无数据");
        }

        GenealogyMemberTreeBO genealogyMemberTreeBO = this.peopleService.listMemberTree(defaultGenealogy.getGenealogyId());
        return genealogyMemberTreeBO == null ? CommonResult.failed("家族人员无数据") : CommonResult.success(genealogyMemberTreeBO);
    }

    @ApiOperation("查询当前用户在默认家族中的人员信息")
    @GetMapping
    public CommonResult<Object> currentUserGenealogyPeople() {
        UserDO currentUser = securityUserService.getCurrentUser();
        // 查询用户默认家族
        GenealogyUserDO defaultGenealogy = this.genealogyUserService.getDefaultGenealogyOfUser(currentUser.getId());
        if (defaultGenealogy == null) {
            return CommonResult.failed("默认家族信息无数据");
        }
        PeopleVO userPeople = this.peopleService.getByUserGenealogyId(currentUser.getId(), defaultGenealogy.getGenealogyId());
        return userPeople == null ? CommonResult.failed("家族中不存在您的人员信息") : CommonResult.success(userPeople);
    }

}

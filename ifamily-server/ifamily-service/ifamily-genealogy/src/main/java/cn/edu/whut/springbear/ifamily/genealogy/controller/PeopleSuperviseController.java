package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.constant.MessageConstants;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.PeopleDetailsBO;
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
 * @since 23/03/31 10:45
 */
@AllArgsConstructor
@Api(tags = "家族人员监督接口")
@RestController
@RequestMapping("/genealogy/people/supervise")
public class PeopleSuperviseController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final PeopleService peopleService;

    @ApiOperation("查询当前用户在默认家族中的人员信息")
    @GetMapping("/current")
    public CommonResult<Object> getCurrentUserPeople() {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        PeopleVO userPeople = this.peopleService.getUserPeople(userDTO.getId(), defaultGenealogy.getGenealogyId());
        return userPeople == null ? CommonResult.failed("您的家族资料无数据") : CommonResult.success(userPeople);
    }

    @ApiOperation("保存当前用户在默认家族中的人员信息")
    @PostMapping("/current")
    public CommonResult<String> saveCurrentUserPeople(@Validated @RequestBody PeopleQuery peopleQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        boolean saveResult = this.peopleService.createUserPeople(peopleQuery, userDTO.getId(), defaultGenealogy.getGenealogyId());
        return saveResult ? CommonResult.success() : CommonResult.failed("请求保存您的人员资料失败");
    }

    @ApiOperation("查询用户默认家族人员详细信息")
    @GetMapping("/details")
    public CommonResult<Object> getPeopleDetails(@ApiParam("家族人员 ID") @RequestParam("peopleId") Long peopleId) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        PeopleDetailsBO peopleDetailsBO = this.peopleService.getPeopleDetails(defaultGenealogy.getGenealogyId(), peopleId, userDTO.getId());
        return peopleDetailsBO == null ? CommonResult.failed(MessageConstants.PEOPLE_INFO_NO_DATA) : CommonResult.success(peopleDetailsBO);
    }

}

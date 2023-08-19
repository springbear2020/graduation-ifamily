package cn.edu.whut.springbear.ifamily.genealogy.controller.admin;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.MessageConstants;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import cn.edu.whut.springbear.ifamily.genealogy.service.GenealogyService;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Spring-_-Bear
 * @since 23/04/15 09:36
 */
@AllArgsConstructor
@Api(tags = "家族信息管理接口")
@RestController
@RequestMapping("/genealogy/admin")
public class GenealogyAdminController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final GenealogyService genealogyService;

    @ApiOperation("更新用户默认家族资料")
    @PutMapping
    public CommonResult<String> editDefaultGenealogy(@Validated @RequestBody GenealogyQuery genealogyQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        // 更新用户默认家族信息
        genealogyQuery.setId(defaultGenealogy.getGenealogyId());
        boolean updateResult = this.genealogyService.edit(genealogyQuery);
        return updateResult ? CommonResult.success() : CommonResult.failed(MessageConstants.SYSTEM_EXCEPTION);
    }

}

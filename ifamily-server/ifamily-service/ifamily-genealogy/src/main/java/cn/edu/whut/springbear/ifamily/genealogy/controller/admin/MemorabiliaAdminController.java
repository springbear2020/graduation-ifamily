package cn.edu.whut.springbear.ifamily.genealogy.controller.admin;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.MemorabiliaQuery;
import cn.edu.whut.springbear.ifamily.genealogy.service.MemorabiliaService;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Spring-_-Bear
 * @since 23/05/02 09:52
 */
@RequiredArgsConstructor
@Api(tags = "家族大事管理接口")
@RestController
@RequestMapping("/genealogy/admin/memorabilia")
public class MemorabiliaAdminController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final MemorabiliaService memorabiliaService;

    @ApiOperation("保存用户默认家族大事")
    @PostMapping
    public CommonResult<String> create(@Validated @RequestBody MemorabiliaQuery memorabiliaQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        boolean saveResult = this.memorabiliaService.create(userDTO.getId(), defaultGenealogy.getGenealogyId(), memorabiliaQuery);
        return saveResult ? CommonResult.success() : CommonResult.failed("请求保存默认家族大事失败");
    }

}

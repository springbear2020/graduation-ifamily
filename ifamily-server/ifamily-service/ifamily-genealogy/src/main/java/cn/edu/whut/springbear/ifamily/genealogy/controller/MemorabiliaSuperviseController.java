package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.MemorabiliaQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.MemorabiliaVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.MemorabiliaService;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 12:37
 */
@RequiredArgsConstructor
@Api(tags = "家族大事监督接口")
@RestController
@RequestMapping("/genealogy/memorabilia/supervise")
public class MemorabiliaSuperviseController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final MemorabiliaService memorabiliaService;

    @ApiOperation("保存用户默认家族大事")
    @PostMapping
    public CommonResult<String> create(@Validated @RequestBody MemorabiliaQuery memorabiliaQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        boolean saveResult = this.memorabiliaService.create(userDTO.getId(), defaultGenealogy.getGenealogyId(), memorabiliaQuery);
        return saveResult ? CommonResult.success() : CommonResult.failed("请求保存家族大事失败");
    }

    @ApiOperation("查看用户默认家族大事")
    @GetMapping
    public CommonResult<Object> page(@Validated PageQuery pageQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        List<MemorabiliaVO> pageData = this.memorabiliaService.page(pageQuery, defaultGenealogy.getGenealogyId());
        return pageData == null || pageData.isEmpty() ? CommonResult.failed("家族大事无数据") : CommonResult.success(pageData);
    }

}

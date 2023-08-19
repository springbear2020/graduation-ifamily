package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.RevisionBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.VisitorBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.service.RevisionLogService;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import cn.edu.whut.springbear.ifamily.genealogy.service.VisitorLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/09 17:15
 */
@AllArgsConstructor
@Api(tags = "家族记录监督接口")
@RestController
@RequestMapping("/genealogy/record/supervise")
public class RecordSuperviseController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final RevisionLogService revisionLogService;
    private final VisitorLogService visitorLogService;

    @ApiOperation("查看用户默认家族修谱日志")
    @GetMapping("/revision")
    public CommonResult<Object> revisionLog(@Validated PageQuery pageQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        List<RevisionBO> pageData = this.revisionLogService.page(pageQuery, defaultGenealogy.getGenealogyId());
        return pageData == null || pageData.isEmpty() ? CommonResult.failed("家族修谱日志无数据") : CommonResult.success(pageData);
    }

    @ApiOperation("查看用户默认家族访问记录")
    @GetMapping("/visitor")
    public CommonResult<Object> visitorLog(@Validated PageQuery pageQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        List<VisitorBO> pageData = this.visitorLogService.page(pageQuery, defaultGenealogy.getGenealogyId());
        return pageData == null || pageData.isEmpty() ? CommonResult.failed("家族访问记录无数据") : CommonResult.success(pageData);
    }

}

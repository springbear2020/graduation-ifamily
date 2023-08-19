package cn.edu.whut.springbear.ifamily.genealogy.controller.supervise;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.*;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.query.GenealogyQuery;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.vo.MemorabiliaVO;
import cn.edu.whut.springbear.ifamily.genealogy.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 15:59
 */
@AllArgsConstructor
@Api(tags = "家族监督接口")
@RestController
@RequestMapping("/genealogy/supervise")
public class GenealogySuperviseController {

    private final HttpServletRequest httpServletRequest;
    private final GenealogyService genealogyService;
    private final UserGenealogyService userGenealogyService;
    private final NoticeService noticeService;
    private final MemorabiliaService memorabiliaService;
    private final PhotoService photoService;
    private final RevisionLogService revisionLogService;
    private final VisitorLogService visitorLogService;

    @ApiOperation("用户创建家族")
    @PostMapping
    public CommonResult<String> create(@Validated @RequestBody GenealogyQuery genealogyQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        boolean saveResult = this.genealogyService.create(genealogyQuery, userDTO.getId());
        return saveResult ? CommonResult.success() : CommonResult.failed("请求创建家族失败");
    }

    @ApiOperation("设置用户默认家族")
    @PutMapping("/{genealogyId}")
    public CommonResult<String> setDefault(@ApiParam("家族 ID") @PathVariable("genealogyId") Long genealogyId) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        boolean updateResult = this.userGenealogyService.setDefault(userDTO.getId(), genealogyId);
        return updateResult ? CommonResult.success() : CommonResult.failed("请求设置默认家族失败");
    }

    @ApiOperation("查询用户家族列表")
    @GetMapping
    public CommonResult<Object> list() {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        List<GenealogyDetailsBO> genealogiesOfUser = this.genealogyService.listWithDetails(userDTO.getId());
        return genealogiesOfUser == null || genealogiesOfUser.isEmpty() ? CommonResult.failed("家族列表无数据") : CommonResult.success(genealogiesOfUser);
    }

    @ApiOperation("查看用户默认家族公告分页数据")
    @GetMapping("/notice")
    public CommonResult<Object> noticePageData(@Validated PageQuery pageQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        List<NoticeBO> pageData = this.noticeService.page(pageQuery, defaultGenealogy.getGenealogyId());
        return pageData == null || pageData.isEmpty() ? CommonResult.failed("默认家族公告无数据") : CommonResult.success(pageData);
    }

    @ApiOperation("查看用户默认家族大事分页数据")
    @GetMapping("/memorabilia")
    public CommonResult<Object> memorabiliaPageData(@Validated PageQuery pageQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        List<MemorabiliaVO> pageData = this.memorabiliaService.page(pageQuery, defaultGenealogy.getGenealogyId());
        return pageData == null || pageData.isEmpty() ? CommonResult.failed("默认家族大事无数据") : CommonResult.success(pageData);
    }

    @ApiOperation("查看用户默认家族相册图片分页数据")
    @GetMapping("/photo")
    public CommonResult<Object> photoPageData(@Validated PageQuery pageQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        List<PhotoBO> pageData = this.photoService.page(pageQuery, defaultGenealogy.getGenealogyId());
        return pageData == null || pageData.isEmpty() ? CommonResult.failed("默认家族相册图片无数据") : CommonResult.success(pageData);
    }

    @ApiOperation("查看用户默认家族修谱日志分页数据")
    @GetMapping("/revision")
    public CommonResult<Object> revisionLog(@Validated PageQuery pageQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        List<RevisionBO> pageData = this.revisionLogService.page(pageQuery, defaultGenealogy.getGenealogyId());
        return pageData == null || pageData.isEmpty() ? CommonResult.failed("默认家族修谱日志无数据") : CommonResult.success(pageData);
    }

    @ApiOperation("查看用户默认家族访问记录分页数据")
    @GetMapping("/visitor")
    public CommonResult<Object> visitorLog(@Validated PageQuery pageQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        List<VisitorBO> pageData = this.visitorLogService.page(pageQuery, defaultGenealogy.getGenealogyId());
        return pageData == null || pageData.isEmpty() ? CommonResult.failed("默认家族访问记录无数据") : CommonResult.success(pageData);
    }

}

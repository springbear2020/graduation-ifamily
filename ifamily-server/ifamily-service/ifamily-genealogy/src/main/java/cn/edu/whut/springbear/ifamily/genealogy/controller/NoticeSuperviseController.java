package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.bo.NoticeBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.service.NoticeService;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/15 20:51
 */
@AllArgsConstructor
@Api(tags = "家族公告监督接口")
@RestController
@RequestMapping("/genealogy/notice/supervise")
public class NoticeSuperviseController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final NoticeService noticeService;

    @ApiOperation("发布用户默认家族公告")
    @PostMapping
    public CommonResult<String> create(@ApiParam("公告内容") @RequestParam("content") @Size(max = 1000, message = "请填写家族公告，长度不大于 1000") String content) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        boolean saveResult = this.noticeService.create(defaultGenealogy.getGenealogyId(), userDTO.getId(), content);
        return saveResult ? CommonResult.success() : CommonResult.failed("请求发布家族公告失败");
    }

    @ApiOperation("查看用户默认家族公告")
    @GetMapping
    public CommonResult<Object> page(@Validated PageQuery pageQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        List<NoticeBO> pageData = this.noticeService.page(pageQuery, defaultGenealogy.getGenealogyId());
        return pageData == null || pageData.isEmpty() ? CommonResult.failed("家族公告无数据") : CommonResult.success(pageData);
    }

}

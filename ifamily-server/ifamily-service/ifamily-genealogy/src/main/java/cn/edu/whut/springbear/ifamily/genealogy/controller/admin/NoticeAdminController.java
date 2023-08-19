package cn.edu.whut.springbear.ifamily.genealogy.controller.admin;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.service.NoticeService;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

/**
 * @author Spring-_-Bear
 * @since 23/05/02 09:50
 */
@AllArgsConstructor
@Api(tags = "家族公告管理接口")
@RestController
@RequestMapping("/genealogy/admin/notice")
public class NoticeAdminController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final NoticeService noticeService;

    @ApiOperation("保存用户默认家族公告")
    @PostMapping
    public CommonResult<String> create(@ApiParam("公告内容") @RequestParam("content") @Size(max = 1000, message = "请填写家族公告，长度不大于 1000") String content) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        boolean saveResult = this.noticeService.create(defaultGenealogy.getGenealogyId(), userDTO.getId(), content);
        return saveResult ? CommonResult.success() : CommonResult.failed("请求保存默认家族公告失败");
    }

}

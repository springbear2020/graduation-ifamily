package cn.edu.whut.springbear.ifamily.genealogy.controller.admin;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.MessageConstants;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.service.NoticeService;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Spring-_-Bear
 * @since 23/04/16 19:11
 */
@AllArgsConstructor
@Api(tags = "家族公告管理接口")
@RestController
@RequestMapping("/genealogy/notice/admin")
public class NoticeAdminController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final NoticeService noticeService;

    @ApiOperation("编辑用户默认家族公告")
    @PutMapping
    public CommonResult<Object> edit(@ApiParam("家族公告 ID") @RequestParam("id") Long id,
                                     @ApiParam("家族公告内容") @RequestParam("content") String content) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        boolean result = this.noticeService.edit(id, content, defaultGenealogy.getGenealogyId());
        return result ? CommonResult.success() : CommonResult.failed(MessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("删除用户默认家族公告")
    @DeleteMapping
    public CommonResult<Object> edit(@ApiParam("家族公告 ID") @RequestParam("id") Long id) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        boolean result = this.noticeService.remove(id, defaultGenealogy.getGenealogyId());
        return result ? CommonResult.success() : CommonResult.failed(MessageConstants.SYSTEM_EXCEPTION);
    }

}

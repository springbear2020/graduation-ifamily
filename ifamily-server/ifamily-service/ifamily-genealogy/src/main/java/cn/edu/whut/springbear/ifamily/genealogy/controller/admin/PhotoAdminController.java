package cn.edu.whut.springbear.ifamily.genealogy.controller.admin;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.service.PhotoService;
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
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/05/02 09:54
 */
@AllArgsConstructor
@Api(tags = "家族图片管理接口")
@RestController
@RequestMapping("/genealogy/admin/photo")
public class PhotoAdminController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final PhotoService photoService;

    @ApiOperation("保存用户默认家族相册图片")
    @PostMapping
    public CommonResult<Object> create(@ApiParam("相册图片列表") @RequestParam("imgUrls") List<String> imgUrls) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        boolean saveResult = this.photoService.create(defaultGenealogy.getGenealogyId(), userDTO.getId(), imgUrls);
        return saveResult ? CommonResult.success() : CommonResult.failed("请求保存默认家族相册图片失败");
    }

}

package cn.edu.whut.springbear.ifamily.genealogy.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.pojo.query.PageQuery;
import cn.edu.whut.springbear.ifamily.common.util.WebUtils;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.PhotoBO;
import cn.edu.whut.springbear.ifamily.genealogy.pojo.po.UserGenealogyDO;
import cn.edu.whut.springbear.ifamily.genealogy.service.PhotoService;
import cn.edu.whut.springbear.ifamily.genealogy.service.UserGenealogyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/28 09:33
 */
@AllArgsConstructor
@Api(tags = "家族图片监督接口")
@RestController
@RequestMapping("/genealogy/photo/supervise")
public class PhotoSuperviseController {

    private final HttpServletRequest httpServletRequest;
    private final UserGenealogyService userGenealogyService;
    private final PhotoService photoService;

    @ApiOperation("保存用户默认家族相册图片")
    @PostMapping
    public CommonResult<Object> getCurrentUserPeople(@ApiParam("相册图片列表") @RequestParam("imgUrls[]") String[] imgUrls) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        boolean saveResult = this.photoService.create(defaultGenealogy.getGenealogyId(), userDTO.getId(), Arrays.asList(imgUrls));
        return saveResult ? CommonResult.success() : CommonResult.failed("请求保存家族相册图片失败");
    }

    @ApiOperation("查看用户默认家族相册图片")
    @GetMapping
    public CommonResult<Object> page(@Validated PageQuery pageQuery) {
        UserDTO userDTO = WebUtils.parseGeneralUser(httpServletRequest);
        UserGenealogyDO defaultGenealogy = this.userGenealogyService.getDefault(userDTO.getId());
        List<PhotoBO> pageData = this.photoService.page(pageQuery, defaultGenealogy.getGenealogyId());
        return pageData == null || pageData.isEmpty() ? CommonResult.failed("家族相册图片无数据") : CommonResult.success(pageData);
    }

}

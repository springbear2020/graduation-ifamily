package cn.edu.whut.springbear.ifamily.manager.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.SystemMessageConstants;
import cn.edu.whut.springbear.ifamily.manager.service.QiniuService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 16:30
 */
@Api(tags = "文件上传监督管理接口")
@RestController
@RequestMapping("/supervise/manager/uploader")
public class UploaderSuperviseController {

    @Autowired
    private QiniuService qiniuService;

    @ApiOperation("获取七牛云头像图片上传令牌")
    @GetMapping("/qiniu/img/token")
    public CommonResult<Object> qiniuImageToken(@RequestParam("suffix") String suffix, @RequestParam("type") Integer type) {
        // [1]用户头像 [2]家族封面
        String imgType;
        switch (type) {
            case 1:
                imgType = "avatar";
                break;
            case 2:
                imgType = "cover";
                break;
            default:
                return CommonResult.failed("图片类型不正确");
        }

        // 自定义文件上传名，例：img/avatar/23/03/27/a5c8a5e8-df2b-4706-bea4-08d0939410e3.png
        String key = "img/" + imgType + "/" + DateUtil.format(new Date(), "yyyy/MM/dd") + "/" + IdUtil.randomUUID() + suffix;
        Map<String, String> map = qiniuService.imgToken(key);
        return map == null ? CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION) : CommonResult.success(map);
    }

}

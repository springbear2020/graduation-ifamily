package cn.edu.whut.springbear.ifamily.manager.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 16:30
 */
@Api(tags = "文件上传监督管理接口")
@RestController
@RequestMapping("/supervise/manager/uploader")
public class UploaderSuperviseController {

    @ApiOperation("获取七牛云图片上传令牌")
    @GetMapping("/qiniu/img/token")
    public CommonResult<String> qiniuImageToken() {
        return CommonResult.success("get token success");
    }

}

package cn.edu.whut.springbear.ifamily.manager.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.manager.service.business.QiniuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 16:30
 */
@AllArgsConstructor
@Api(tags = "文件上传监督接口")
@RestController
@RequestMapping("/manager/transfer/supervise")
public class TransferSuperviseController {

    private final QiniuService qiniuService;

    @ApiOperation("获取七牛云图片上传令牌")
    @GetMapping("/upload/qiniu/img/token")
    public CommonResult<Object> qiniuImageToken(
            @ApiParam("文件扩展名") @RequestParam("suffix") String suffix,
            @ApiParam("图片类型：[1]用户头像 [2]家族封面 [3]人物肖像 [4]家族相册 [5]家族大事") @RequestParam("type") Integer type) {
        Map<String, String> resultMap = qiniuService.imgToken(suffix, type);
        return CommonResult.success(resultMap);
    }

}

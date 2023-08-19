package cn.edu.whut.springbear.ifamily.manager.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.MessageConstants;
import cn.edu.whut.springbear.ifamily.manager.enumerate.ImageTypeEnum;
import cn.edu.whut.springbear.ifamily.manager.service.business.QiniuService;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Api(tags = "文件上传监督管理接口")
@RestController
@RequestMapping("/manager/supervise/uploader")
public class UploaderSuperviseController {

    private final QiniuService qiniuService;

    @ApiOperation("获取七牛云图片上传令牌")
    @GetMapping("/qiniu/img/token")
    public CommonResult<Object> qiniuImageToken(
            @ApiParam("文件扩展名") @RequestParam("suffix") String suffix,
            @ApiParam("图片类型：[1]用户头像 [2]家族封面 [3]人物肖像") @RequestParam("type") Integer type) {
        // [1]用户头像 [2]家族封面 [3]人物肖像
        String imgType;
        switch (type) {
            case 1:
                imgType = ImageTypeEnum.USER_AVATAR.getDirectory();
                break;
            case 2:
                imgType = ImageTypeEnum.CLAN_COVER.getDirectory();
                break;
            case 3:
                imgType = ImageTypeEnum.PEOPLE_PORTRAIT.getDirectory();
                break;
            default:
                return CommonResult.failed("图片类型不正确");
        }

        // 自定义文件上传名，例：img/avatar/23/03/27/a5c8a5e8-df2b-4706-bea4-08d0939410e3.png
        String key = "img/" + imgType + "/" + DateUtil.format(new Date(), "yyyy/MM/dd") + "/" + IdUtil.randomUUID() + suffix;
        // 创建 token，创建成功返回 token, cdn, key
        Map<String, String> map = qiniuService.imgToken(key);
        return map == null || map.isEmpty() ? CommonResult.failed(MessageConstants.SYSTEM_EXCEPTION) : CommonResult.success(map);
    }

}

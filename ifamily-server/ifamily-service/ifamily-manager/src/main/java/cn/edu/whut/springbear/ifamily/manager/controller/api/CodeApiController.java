package cn.edu.whut.springbear.ifamily.manager.controller.api;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.manager.service.business.CodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring-_-Bear
 * @since 23/04/14 10:25
 */
@AllArgsConstructor
@RestController
@Api(tags = "验证码开放接口")
@RequestMapping("/manager/api/code")
public class CodeApiController {

    private final CodeService codeService;

    @ApiOperation("发送邮箱验证码")
    @PostMapping("/email")
    public CommonResult<String> sendEmailCode(@ApiParam("邮箱地址") @RequestParam("email") String email) {
        boolean result = this.codeService.sendEmailCode(email);
        return result ? CommonResult.success() : CommonResult.failed("请求发送邮箱验证码失败");
    }

    @ApiOperation("发送手机验证码")
    @PostMapping("/phone")
    public CommonResult<String> sendPhoneCode(@ApiParam("手机号") @RequestParam("phone") String phone) {
        boolean result = codeService.sendPhoneCode(phone);
        return result ? CommonResult.success() : CommonResult.failed("请求发送手机验证码失败");
    }

}

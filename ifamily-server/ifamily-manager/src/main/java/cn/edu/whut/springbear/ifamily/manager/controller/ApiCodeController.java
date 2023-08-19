package cn.edu.whut.springbear.ifamily.manager.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.manager.constant.CodeMessageConstants;
import cn.edu.whut.springbear.ifamily.manager.service.CodeService;
import cn.hutool.core.util.ReUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring-_-Bear
 * @since 23/03/23 08:55
 */
@RestController
@Api(tags = "验证码服务接口")
@RequestMapping("/api/manager/code")
public class ApiCodeController {

    @Autowired
    private CodeService codeService;

    @ApiOperation("邮箱验证码发送")
    @PostMapping("/email")
    public CommonResult<String> sendEmailCode(@ApiParam("邮箱地址") @RequestParam("email") String email) {
        String emailRegExp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        if (!ReUtil.isMatch(emailRegExp, email)) {
            return CommonResult.failed("请输入正确格式的邮箱地址");
        }
        return codeService.sendEmailCode(email) ? CommonResult.success() : CommonResult.failed(CodeMessageConstants.CODE_SEND_FAILED);
    }

    @ApiOperation("手机验证码发送")
    @PostMapping("/phone")
    public CommonResult<String> sendPhoneCode(@ApiParam("手机号") @RequestParam("phone") String phone) {
        String phoneRegExp = "^1[3456789]\\d{9}$";
        if (!ReUtil.isMatch(phoneRegExp, phone)) {
            return CommonResult.failed("请输入正确格式的手机号");
        }
        return codeService.sendSmsCode(phone) ? CommonResult.success() : CommonResult.failed(CodeMessageConstants.CODE_SEND_FAILED);
    }

}

package cn.edu.whut.springbear.ifamily.user.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.exception.SystemServiceException;
import cn.edu.whut.springbear.ifamily.user.constant.UserMessageConstants;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserLoginQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserQuery;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 19:45
 */
@Api(tags = "用户服务接口")
@RestController
@RequestMapping("/api/user")
public class ApiUserController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public CommonResult<String> login(@Validated @RequestBody UserLoginQuery userLoginQuery) {
        String token = this.userService.login(userLoginQuery);
        return CommonResult.success(token);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public CommonResult<String> userRegister(@RequestBody UserQuery userQuery) {
        if (this.userService.create(userQuery)) {
            return CommonResult.success();
        }
        throw new SystemServiceException(UserMessageConstants.SQL_EXECUTION_ERROR);
    }

    @ApiOperation("信息验证")
    @GetMapping("/verify/{type}")
    public CommonResult<String> verifyUserInfo(
            @ApiParam("验证类型：[1]验证用户名 [2]验证手机 [3]验证邮箱") @PathVariable("type") Integer type,
            @ApiParam("需要验证的内容") @RequestParam("content") String content) {

        // [1]验证用户名 [2]验证手机 [3]验证邮箱
        if (!(1 == type || 2 == type || 3 == type)) {
            return CommonResult.failed("请求类型不正确：[1]验证用户名 [2]验证手机 [3]验证邮箱");
        }
        String result = this.userService.verify(type, content);
        return result == null ? CommonResult.failed(UserMessageConstants.USER_NOT_EXISTS) : CommonResult.success(result);
    }

}

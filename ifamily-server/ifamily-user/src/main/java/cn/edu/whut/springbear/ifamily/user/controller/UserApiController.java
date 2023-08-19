package cn.edu.whut.springbear.ifamily.user.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.common.constant.SystemMessageConstants;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserLoginQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.query.UserResetQuery;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 19:45
 */
@Api(tags = "用户开放服务接口")
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public CommonResult<String> login(@Validated @RequestBody UserLoginQuery userLoginQuery) {
        String token = this.userService.login(userLoginQuery);
        return token != null ? CommonResult.success(token) : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("重置密码")
    @PutMapping("/reset")
    public CommonResult<String> reset(@Validated @RequestBody UserResetQuery userResetQuery) {
        boolean result = this.userService.reset(userResetQuery);
        return result ? CommonResult.success() : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public CommonResult<String> userRegister(@Validated @RequestBody UserResetQuery userResetQuery) {
        String token = this.userService.register(userResetQuery);
        return token != null ? CommonResult.success(token) : CommonResult.failed(SystemMessageConstants.SYSTEM_EXCEPTION);
    }

}

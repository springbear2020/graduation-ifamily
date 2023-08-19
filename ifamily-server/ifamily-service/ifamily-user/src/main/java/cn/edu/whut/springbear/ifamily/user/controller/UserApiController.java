package cn.edu.whut.springbear.ifamily.user.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.edu.whut.springbear.ifamily.user.pojo.query.LoginQuery;
import cn.edu.whut.springbear.ifamily.user.pojo.query.ResetQuery;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 19:45
 */
@AllArgsConstructor
@Api(tags = "用户开放接口")
@RestController
@RequestMapping("/user/api")
public class UserApiController {

    private final UserService userService;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public CommonResult<Object> login(@Validated @RequestBody LoginQuery loginQuery) {
        return this.userService.login(loginQuery);
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public CommonResult<Object> register(@Validated @RequestBody ResetQuery resetQuery) {
        return this.userService.register(resetQuery);
    }

    @ApiOperation("重置密码")
    @PutMapping("/reset")
    public CommonResult<String> reset(@Validated @RequestBody ResetQuery resetQuery) {
        boolean updateResult = this.userService.reset(resetQuery);
        return updateResult ? CommonResult.success() : CommonResult.failed("请求更新密码失败");
    }

}

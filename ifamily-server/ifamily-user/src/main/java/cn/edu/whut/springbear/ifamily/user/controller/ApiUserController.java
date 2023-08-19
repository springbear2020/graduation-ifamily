package cn.edu.whut.springbear.ifamily.user.controller;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 19:45
 */
@Api(tags = "用户接口")
@RestController
@RequestMapping("/api/user")
public class ApiUserController {

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public CommonResult<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        return CommonResult.success("username: " + username + " password: " + password);
    }

}

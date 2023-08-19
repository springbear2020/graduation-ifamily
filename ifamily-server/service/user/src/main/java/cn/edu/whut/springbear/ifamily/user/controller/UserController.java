package cn.edu.whut.springbear.ifamily.user.controller;


import cn.edu.whut.springbear.ifamily.api.CommonResult;
import cn.edu.whut.springbear.ifamily.domain.po.User;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring-_-Bear
 * @since 2023-03-10
 */
@RestController
@Api(tags = "用户接口")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ApiOperation("用户注册")
    public CommonResult<Object> userRegister(User user) {
        return userService.saveUser(user) ? CommonResult.success() : CommonResult.failed();
    }
}


package cn.edu.whut.springbear.ifamily.user.controller;

import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 20:56
 */
@Api(tags = "用户服务调用接口")
@AllArgsConstructor
@RestController
@RequestMapping("/user/feign")
public class UserFeignController {

    private final UserService userService;

    @ApiOperation("根据用户名查询用户信息")
    @GetMapping
    public UserDTO loadUserByUsername(@ApiParam("用户名") @RequestParam String username) {
        return this.userService.loadUserByUsername(username);
    }

}

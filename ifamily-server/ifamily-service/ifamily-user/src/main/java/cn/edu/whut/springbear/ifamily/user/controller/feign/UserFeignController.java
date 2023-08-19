package cn.edu.whut.springbear.ifamily.user.controller.feign;

import cn.edu.whut.springbear.ifamily.common.pojo.vo.CommonUserVO;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 20:56
 */
@Api(tags = "用户调用接口")
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

    @ApiOperation("根据 ID 查询用户信息")
    @GetMapping("/{userId}")
    public CommonUserVO getById(@ApiParam("用户 ID") @PathVariable("userId") Long userId) {
        return this.userService.getUserById(userId);
    }

}

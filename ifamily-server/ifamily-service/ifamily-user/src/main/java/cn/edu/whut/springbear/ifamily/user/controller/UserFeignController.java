package cn.edu.whut.springbear.ifamily.user.controller;

import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 20:56
 */
@AllArgsConstructor
@RestController
@RequestMapping("/user/feign")
public class UserFeignController {

    private final UserService userService;

    @GetMapping
    public UserDTO loadUserByUsername(@RequestParam String username) {
        return this.userService.loadUserByUsername(username);
    }

}

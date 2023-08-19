package cn.edu.whut.springbear.ifamily.user.controller;

import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import cn.edu.whut.springbear.ifamily.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 20:56
 */
@RestController
@RequestMapping("/feign/user")
public class UserFeignController {

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public UserDO getUserByUsername(@RequestParam("username") String username) {
        return this.userService.getUserByUsername(username);
    }

}

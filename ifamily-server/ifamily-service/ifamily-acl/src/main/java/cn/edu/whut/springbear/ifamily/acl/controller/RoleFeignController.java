package cn.edu.whut.springbear.ifamily.acl.controller;

import cn.edu.whut.springbear.ifamily.acl.service.AdminRoleService;
import cn.edu.whut.springbear.ifamily.acl.service.UserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 20:31
 */
@AllArgsConstructor
@RestController
@RequestMapping("/acl/feign/role")
public class RoleFeignController {

    private final AdminRoleService adminRoleService;
    private final UserRoleService userRoleService;

    @GetMapping("/admin")
    public List<String> listRoleNamesOfAdmin(@RequestParam("adminId") Long adminId) {
        return this.adminRoleService.listRoleNamesOfAdmin(adminId);
    }

    @GetMapping("/user")
    public List<String> listRoleNamesOfUser(@RequestParam("userId") Long userId) {
        return this.userRoleService.listRoleNamesOfUser(userId);
    }

}

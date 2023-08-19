package cn.edu.whut.springbear.ifamily.admin.controller;

import cn.edu.whut.springbear.ifamily.admin.service.AdminService;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 16:33
 */
@AllArgsConstructor
@RestController
@RequestMapping("/admin/feign")
public class AdminFeignController {

    private final AdminService adminService;

    @GetMapping
    public UserDTO loadAdminByUsername(@RequestParam String username) {
        return this.adminService.loadAdminByUsername(username);
    }

}

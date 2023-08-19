package cn.edu.whut.springbear.ifamily.admin.controller;

import cn.edu.whut.springbear.ifamily.admin.service.AdminService;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
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
 * @since 23/04/12 16:33
 */
@Api(tags = "管理员服务调用接口")
@AllArgsConstructor
@RestController
@RequestMapping("/admin/feign")
public class AdminFeignController {

    private final AdminService adminService;

    @ApiOperation("根据用户名查询管理员信息")
    @GetMapping
    public UserDTO loadAdminByUsername(@ApiParam("用户名") @RequestParam String username) {
        return this.adminService.loadAdminByUsername(username);
    }

}

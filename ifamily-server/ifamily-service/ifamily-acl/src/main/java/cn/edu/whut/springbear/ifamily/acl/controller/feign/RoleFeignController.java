package cn.edu.whut.springbear.ifamily.acl.controller.feign;

import cn.edu.whut.springbear.ifamily.acl.service.AdminRoleService;
import cn.edu.whut.springbear.ifamily.acl.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(tags = "用户角色调用接口")
@AllArgsConstructor
@RestController
@RequestMapping("/acl/feign/role")
public class RoleFeignController {

    private final AdminRoleService adminRoleService;
    private final UserRoleService userRoleService;

    @ApiOperation("查询管理员绑定的所有角色名称")
    @GetMapping("/admin")
    public List<String> listRoleNamesOfAdmin(@ApiParam("管理员 ID") @RequestParam("adminId") Long adminId) {
        return this.adminRoleService.listRoleNamesOfAdmin(adminId);
    }

    @ApiOperation("查询用户绑定的所有角色名称")
    @GetMapping("/user")
    public List<String> listRoleNamesOfUser(@ApiParam("用户 ID") @RequestParam("userId") Long userId) {
        return this.userRoleService.listRoleNamesOfUser(userId);
    }

}

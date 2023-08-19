package cn.edu.whut.springbear.ifamily.acl.controller.feign;

import cn.edu.whut.springbear.ifamily.acl.service.RoleService;
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
@Api(tags = "角色调用接口")
@AllArgsConstructor
@RestController
@RequestMapping("/acl/feign/role")
public class RoleFeignController {

    private final RoleService roleService;

    @ApiOperation("查询管理员对应角色名称集合")
    @GetMapping("/admin")
    public List<String> listRoleNamesOfAdmin(@ApiParam("管理员 ID") @RequestParam("adminId") Long adminId) {
        return this.roleService.listRoleNamesOfAdmin(adminId);
    }

    @ApiOperation("查询用户对应的角色名称集合")
    @GetMapping("/user")
    public List<String> listRoleNamesOfUser(@ApiParam("用户 ID") @RequestParam("userId") Long userId) {
        return this.roleService.listRoleNamesOfUser(userId);
    }

}

package cn.edu.whut.springbear.ifamily.backend.controller.feign;

import cn.edu.whut.springbear.ifamily.backend.service.AdminService;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.common.pojo.vo.RoleUserVO;
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
 * @since 23/04/12 16:33
 */
@Api(tags = "管理员调用接口")
@AllArgsConstructor
@RestController
@RequestMapping("/backend/feign")
public class AdminFeignController {

    private final AdminService adminService;

    @ApiOperation("根据用户名查询管理员信息")
    @GetMapping
    public UserDTO loadAdminByUsername(@ApiParam("用户名") @RequestParam String username) {
        return this.adminService.loadAdminByUsername(username);
    }

    @ApiOperation("批量查询管理员信息")
    @GetMapping("/admin/list")
    public List<RoleUserVO> listInBatchIds(@ApiParam("管理员 ID 集合") @RequestParam List<Long> adminIds) {
        return this.adminService.listInBatchIds(adminIds);
    }

}

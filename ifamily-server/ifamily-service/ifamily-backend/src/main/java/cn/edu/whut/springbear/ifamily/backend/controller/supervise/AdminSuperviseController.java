package cn.edu.whut.springbear.ifamily.backend.controller.supervise;

import cn.edu.whut.springbear.ifamily.backend.pojo.bo.AdminBO;
import cn.edu.whut.springbear.ifamily.backend.service.AdminService;
import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring-_-Bear
 * @since 23/05/04 08:42
 */
@RequiredArgsConstructor
@Api(tags = "管理员监督接口")
@RestController
@RequestMapping("/backend/supervise/admin")
public class AdminSuperviseController {

    private final AdminService adminService;

    @ApiOperation("获取当前登录管理员")
    @GetMapping
    public CommonResult<Object> currentAdmin() {
        AdminBO adminBO = this.adminService.current();
        return adminBO == null ? CommonResult.failed("管理员信息无数据") : CommonResult.success(adminBO);
    }

}

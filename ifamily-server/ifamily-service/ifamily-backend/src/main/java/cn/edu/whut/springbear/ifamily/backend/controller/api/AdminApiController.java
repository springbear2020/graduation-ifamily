package cn.edu.whut.springbear.ifamily.backend.controller.api;

import cn.edu.whut.springbear.ifamily.backend.pojo.query.LoginQuery;
import cn.edu.whut.springbear.ifamily.backend.service.AdminService;
import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Spring-_-Bear
 * @since 23/05/03 21:46
 */
@RequiredArgsConstructor
@Api(tags = "管理员开放接口")
@RestController
@RequestMapping("/backend/api/admin")
public class AdminApiController {

    private final AdminService adminService;

    @ApiOperation("管理员登录")
    @PostMapping("/login")
    public CommonResult<Object> login(@Validated @RequestBody LoginQuery loginQuery) {
        return this.adminService.login(loginQuery);
    }

}

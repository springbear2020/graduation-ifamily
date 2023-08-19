package cn.edu.whut.springbear.ifamily.acl.controller.feign;

import cn.edu.whut.springbear.ifamily.acl.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 20:31
 */
@Api(tags = "菜单调用接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/acl/feign/menu")
public class MenuFeignController {

    private final MenuService menuService;

    @ApiOperation("查询管理员对应菜单路径集合")
    @GetMapping("/admin")
    public List<String> listMenuPathsOfAdmin(@ApiParam("管理员 ID") @RequestParam("adminId") Long adminId) {
        return this.menuService.listMenuPathsOfAdmin(adminId);
    }

}

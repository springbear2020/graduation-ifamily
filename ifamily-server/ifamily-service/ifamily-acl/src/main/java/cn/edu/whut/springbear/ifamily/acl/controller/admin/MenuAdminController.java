package cn.edu.whut.springbear.ifamily.acl.controller.admin;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.MenuDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.query.MenuQuery;
import cn.edu.whut.springbear.ifamily.acl.service.MenuService;
import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import cn.hutool.core.collection.CollUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/05/06 15:39
 */
@Api(tags = "菜单管理接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/acl/admin/menu")
public class MenuAdminController {

    private final MenuService menuService;

    @ApiOperation("新增菜单资源")
    @PostMapping
    public CommonResult<Object> create(@Validated @RequestBody MenuQuery menuQuery) {
        boolean saveResult = this.menuService.create(menuQuery);
        return saveResult ? CommonResult.success() : CommonResult.failed("请求保存菜单资源失败");
    }

    @ApiOperation("查询菜单列表")
    @GetMapping
    public CommonResult<Object> listMenus() {
        List<MenuDO> allRoles = this.menuService.list();
        return CollUtil.isEmpty(allRoles) ? CommonResult.failed("菜单列表无数据") : CommonResult.success(allRoles);
    }

    @ApiOperation("删除菜单资源")
    @DeleteMapping("/{menuId}")
    public CommonResult<Object> removeById(@PathVariable Long menuId) {
        boolean deleteResult = this.menuService.removeById(menuId);
        return deleteResult ? CommonResult.success() : CommonResult.failed("请求删除菜单资源失败");
    }

    @ApiOperation("更新菜单资源")
    @PutMapping
    public CommonResult<Object> edit(@Validated @RequestBody MenuQuery menuQuery) {
        boolean updateResult = this.menuService.edit(menuQuery);
        return updateResult ? CommonResult.success() : CommonResult.failed("请求更新菜单资源失败");
    }

}

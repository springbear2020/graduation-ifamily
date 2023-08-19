package cn.edu.whut.springbear.ifamily.acl.controller.admin;

import cn.edu.whut.springbear.ifamily.acl.pojo.bo.RoleBO;
import cn.edu.whut.springbear.ifamily.acl.pojo.query.RoleQuery;
import cn.edu.whut.springbear.ifamily.acl.service.RoleService;
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
 * @since 23/05/05 08:12
 */
@Api(tags = "角色管理接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/acl/admin/role")
public class RoleAdminController {

    private final RoleService roleService;

    @ApiOperation("查询角色列表")
    @GetMapping
    public CommonResult<Object> listRoles() {
        List<RoleBO> allRoles = this.roleService.listWithDetails();
        return CollUtil.isEmpty(allRoles) ? CommonResult.failed("角色列表无数据") : CommonResult.success(allRoles);
    }

    @ApiOperation("新增系统角色")
    @PostMapping
    public CommonResult<Object> create(@Validated @RequestBody RoleQuery roleQuery) {
        boolean saveResult = this.roleService.create(roleQuery);
        return saveResult ? CommonResult.success() : CommonResult.failed("请求保存角色失败");
    }

    @ApiOperation("删除系统角色")
    @DeleteMapping("/{roleId}")
    public CommonResult<Object> removeById(@PathVariable Long roleId) {
        boolean deleteResult = this.roleService.removeRole(roleId);
        return deleteResult ? CommonResult.success() : CommonResult.failed("请求删除角色失败");
    }

    @ApiOperation("更新系统角色")
    @PutMapping
    public CommonResult<Object> edit(@Validated @RequestBody RoleQuery roleQuery) {
        boolean updateResult = this.roleService.edit(roleQuery);
        return updateResult ? CommonResult.success() : CommonResult.failed("请求更新角色失败");
    }

}

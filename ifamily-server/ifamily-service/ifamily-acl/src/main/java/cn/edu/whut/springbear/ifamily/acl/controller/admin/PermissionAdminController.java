package cn.edu.whut.springbear.ifamily.acl.controller.admin;

import cn.edu.whut.springbear.ifamily.acl.pojo.po.PermissionDO;
import cn.edu.whut.springbear.ifamily.acl.pojo.query.PermissionQuery;
import cn.edu.whut.springbear.ifamily.acl.service.PermissionService;
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
@Api(tags = "权限管理接口")
@RequiredArgsConstructor
@RestController
@RequestMapping("/acl/admin/permission")
public class PermissionAdminController {

    private final PermissionService permissionService;

    @ApiOperation("查询权限列表")
    @GetMapping
    public CommonResult<Object> listPermissions() {
        List<PermissionDO> allRoles = this.permissionService.list();
        return CollUtil.isEmpty(allRoles) ? CommonResult.failed("权限列表无数据") : CommonResult.success(allRoles);
    }

    @ApiOperation("删除权限资源")
    @DeleteMapping("/{permissionId}")
    public CommonResult<Object> removeById(@PathVariable Long permissionId) {
        boolean deleteResult = this.permissionService.removeById(permissionId);
        return deleteResult ? CommonResult.success() : CommonResult.failed("请求删除权限资源失败");
    }

    @ApiOperation("新增权限资源")
    @PostMapping
    public CommonResult<Object> create(@Validated @RequestBody PermissionQuery permissionQuery) {
        boolean saveResult = this.permissionService.create(permissionQuery);
        return saveResult ? CommonResult.success() : CommonResult.failed("请求保存权限资源失败");
    }

    @ApiOperation("更新权限资源")
    @PutMapping
    public CommonResult<Object> edit(@Validated @RequestBody PermissionQuery permissionQuery) {
        boolean updateResult = this.permissionService.edit(permissionQuery);
        return updateResult ? CommonResult.success() : CommonResult.failed("请求更新权限资源失败");
    }

}

package cn.edu.whut.springbear.ifamily.acl.controller;

import cn.edu.whut.springbear.ifamily.acl.service.PermissionService;
import cn.edu.whut.springbear.ifamily.model.po.PermissionDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 22:02
 */
@RestController
@RequestMapping("/feign/acl")
public class AclFeignController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 查询用户拥有的所有系统权限
     */
    @GetMapping("/permissions/user")
    public List<PermissionDO> listPermissionsOfUser(@RequestParam("userId") Long userId) {
        return this.permissionService.listPermissionsOfUser(userId);
    }

    /**
     * 查询系统所有权限
     */
    @GetMapping("/permissions/all")
    public List<PermissionDO> listPermissions() {
        return this.permissionService.list();
    }

}

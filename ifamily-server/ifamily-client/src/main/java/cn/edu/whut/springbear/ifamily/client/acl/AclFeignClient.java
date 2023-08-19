package cn.edu.whut.springbear.ifamily.client.acl;

import cn.edu.whut.springbear.ifamily.model.po.PermissionDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 22:00
 */
@FeignClient("ifamily-acl")
@RequestMapping("/feign/acl")
public interface AclFeignClient {

    /**
     * 查询用户拥有的所有系统权限
     */
    @GetMapping("/user")
    List<PermissionDO> listPermissionsOfUser(@RequestParam("userId") Long userId);

    /**
     * 查询系统所有权限
     */
    @GetMapping("/all")
    List<PermissionDO> listAllPermissions();

}

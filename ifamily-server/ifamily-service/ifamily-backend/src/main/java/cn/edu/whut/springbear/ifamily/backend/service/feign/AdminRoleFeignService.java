package cn.edu.whut.springbear.ifamily.backend.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 20:55
 */
@FeignClient("ifamily-acl")
public interface AdminRoleFeignService {

    /**
     * 查询管理员对应的角色名称集合
     *
     * @param adminId 管理员 ID
     * @return 角色名称集合
     */
    @GetMapping("/acl/feign/role/admin")
    List<String> listRoleNamesOfAdmin(@RequestParam("adminId") Long adminId);

    /**
     * 查询管理员对应的菜单路径集合
     *
     * @param adminId 管理员 ID
     * @return 菜单路径集合
     */
    @GetMapping("/acl/feign/menu/admin")
    List<String> listMenuPathsOfAdmin(@RequestParam("adminId") Long adminId);

}

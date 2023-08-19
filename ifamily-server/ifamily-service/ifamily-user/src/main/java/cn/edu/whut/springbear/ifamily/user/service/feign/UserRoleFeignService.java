package cn.edu.whut.springbear.ifamily.user.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 20:56
 */
@FeignClient("ifamily-acl")
public interface UserRoleFeignService {

    /**
     * 查询用户对应的角色名称集合
     */
    @GetMapping("/acl/feign/role/user")
    List<String> listRoleNamesOfUser(@RequestParam("userId") Long userId);

}

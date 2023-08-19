package cn.edu.whut.springbear.ifamily.auth.service.feign;

import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 14:49
 */
@FeignClient("ifamily-admin")
public interface AdminFeignService {

    /**
     * 根据用户名获取管理员信息
     *
     * @param username 用户名
     * @return UserDTO
     */
    @GetMapping("/admin/feign")
    UserDTO loadAdminByUsername(@RequestParam String username);

}

package cn.edu.whut.springbear.ifamily.auth.service.feign;

import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 14:48
 */
@FeignClient("ifamily-user")
public interface UserFeignService {

    /**
     * 根据用户名获取用户信息
     *
     * @param username 用户名
     * @return UserDTO
     */
    @GetMapping("/user/feign")
    UserDTO loadUserByUsername(@RequestParam String username);

}

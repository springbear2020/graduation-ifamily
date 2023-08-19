package cn.edu.whut.springbear.ifamily.client.user;

import cn.edu.whut.springbear.ifamily.model.po.UserDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 19:44
 */
@FeignClient("ifamily-user")
@RequestMapping("/feign/user")
public interface UserFeignClient {

    /**
     * 根据用户名查询用户信息
     */
    @GetMapping("/get")
    UserDO getUserByUsername(@RequestParam("username") String username);

}

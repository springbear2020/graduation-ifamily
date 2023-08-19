package cn.edu.whut.springbear.ifamily.social.service.feign;

import cn.edu.whut.springbear.ifamily.common.pojo.vo.CommonUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 14:48
 */
@FeignClient("ifamily-user")
public interface UserFeignService {

    /**
     * 根据 ID 查询用户信息
     *
     * @param userId 用户 ID
     * @return 用户信息
     */
    @GetMapping("/user/feign/{userId}")
    CommonUserVO getById(@PathVariable("userId") Long userId);

}

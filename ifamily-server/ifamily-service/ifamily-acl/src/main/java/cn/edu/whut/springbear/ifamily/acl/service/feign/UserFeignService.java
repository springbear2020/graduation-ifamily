package cn.edu.whut.springbear.ifamily.acl.service.feign;

import cn.edu.whut.springbear.ifamily.common.pojo.vo.RoleUserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/05/08 10:44
 */
@FeignClient("ifamily-user")
public interface UserFeignService {

    /**
     * 批量查询用户信息
     *
     * @param userIds 用户 ID 集合
     * @return 用户信息集合
     */
    @GetMapping("/user/feign/list")
    List<RoleUserVO> listInBatchIds(@RequestParam List<Long> userIds);

}

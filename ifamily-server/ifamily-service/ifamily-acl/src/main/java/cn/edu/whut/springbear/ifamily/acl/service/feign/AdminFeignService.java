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
@FeignClient("ifamily-backend")
public interface AdminFeignService {

    /**
     * 批量查询管理员信息
     *
     * @param adminIds 管理员 ID 集合
     * @return 管理员信息集合
     */
    @GetMapping("/backend/feign/admin/list")
    List<RoleUserVO> listInBatchIds(@RequestParam List<Long> adminIds);

}

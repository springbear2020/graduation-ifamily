package cn.edu.whut.springbear.ifamily.social.service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 14:48
 */
@FeignClient("ifamily-genealogy")
public interface GenealogyFeignService {

    /**
     * 根据用户 ID 查询用户默认家族 ID
     *
     * @param userId 用户 ID
     * @return 用户默认家族 ID
     */
    @GetMapping("/genealogy/feign")
    Long getUserDefaultGenealogyId(@RequestParam Long userId);

}

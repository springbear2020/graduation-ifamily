package cn.edu.whut.springbear.ifamily.backend.service.feign;

import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/04/13 21:44
 */
@FeignClient("ifamily-auth")
public interface AuthFeignService {

    /**
     * 请求认证服务器签发令牌
     *
     * @param params key: grant_type, client_id, client_secret, refresh_token, username, password
     * @return 验证通过签发 token，响应码为 cn.edu.whut.springbear.ifamily.common.api.ResultCodeEnum#SUCCESS
     */
    @PostMapping("/oauth/token")
    CommonResult<Object> postAccessToken(@RequestParam Map<String, String> params);

}

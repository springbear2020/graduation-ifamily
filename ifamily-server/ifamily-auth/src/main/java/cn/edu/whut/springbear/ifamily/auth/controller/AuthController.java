package cn.edu.whut.springbear.ifamily.auth.controller;

import cn.edu.whut.springbear.ifamily.auth.pojo.vo.Oauth2TokenVO;
import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.Principal;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 15:21
 */
@RestController
@AllArgsConstructor
public class AuthController {

    private final TokenEndpoint tokenEndpoint;
    private final KeyPair keyPair;

    @PostMapping("/oauth/token")
    public CommonResult<Object> postAccessToken(
            @RequestParam("grant_type") String grantType, @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret, @RequestParam("refresh_token") String refreshToken,
            @RequestParam("username") String username, @RequestParam("password") String password,
            Principal principal) throws HttpRequestMethodNotSupportedException {

        // 收集、转换请求参数
        Map<String, String> parameters = new HashMap<>(6);
        parameters.put("grant_type", grantType);
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);
        parameters.putIfAbsent("refresh_token", refreshToken);
        parameters.putIfAbsent("username", username);
        parameters.putIfAbsent("password", password);

        // 请求签发 token
        OAuth2AccessToken token = this.tokenEndpoint.postAccessToken(principal, parameters).getBody();
        if (token == null) {
            // FIXME throw RuntimeException ?
            return CommonResult.failed("令牌签发失败");
        }

        Oauth2TokenVO tokenVO = new Oauth2TokenVO();
        tokenVO.setAccessToken(token.getValue());
        tokenVO.setRefreshToken(token.getRefreshToken().getValue());
        tokenVO.setExpires(token.getExpiresIn());
        return CommonResult.success(tokenVO);
    }

    @GetMapping("/rsa/public")
    public Map<String, Object> publicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}

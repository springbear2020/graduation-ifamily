package cn.edu.whut.springbear.ifamily.auth.controller;

import cn.edu.whut.springbear.ifamily.auth.pojo.vo.Oauth2TokenVO;
import cn.edu.whut.springbear.ifamily.common.api.CommonResult;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 15:21
 */
@Api(tags = "认证开放接口")
@RestController
@AllArgsConstructor
public class AuthController {

    private final TokenEndpoint tokenEndpoint;
    private final KeyPair keyPair;

    @ApiOperation("签发认证令牌")
    @PostMapping("/oauth/token")
    public CommonResult<Object> postAccessToken(
            @ApiParam("授权类型：password 或 refresh_token") @RequestParam("grant_type") String grantType,
            @ApiParam("客户端类型：mobile-app 或 backend-app") @RequestParam("client_id") String clientId,
            @ApiParam("客户端密钥") @RequestParam("client_secret") String clientSecret,
            @ApiParam("刷新令牌") @RequestParam(value = "refresh_token", required = false) String refreshToken,
            @ApiParam("用户名") @RequestParam(value = "username", required = false) String username,
            @ApiParam("密码") @RequestParam(value = "password", required = false) String password,
            HttpServletRequest request) throws HttpRequestMethodNotSupportedException {

        // 收集、转换请求参数
        Map<String, String> parameters = new HashMap<>(6);
        parameters.put("grant_type", grantType);
        parameters.put("client_id", clientId);
        parameters.put("client_secret", clientSecret);
        parameters.putIfAbsent("refresh_token", refreshToken);
        parameters.putIfAbsent("username", username);
        parameters.putIfAbsent("password", password);

        // 请求签发 token
        OAuth2AccessToken token = this.tokenEndpoint.postAccessToken(request.getUserPrincipal(), parameters).getBody();
        if (token == null) {
            return CommonResult.failed("令牌签发失败");
        }

        Oauth2TokenVO tokenVO = new Oauth2TokenVO();
        tokenVO.setAccessToken(token.getValue());
        tokenVO.setRefreshToken(token.getRefreshToken().getValue());
        tokenVO.setExpires(token.getExpiresIn());
        return CommonResult.success(tokenVO);
    }

    @ApiOperation("获取 rsa 公钥")
    @GetMapping("/rsa/public")
    public Map<String, Object> publicKey() {
        RSAPublicKey publicKey = (RSAPublicKey) this.keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}

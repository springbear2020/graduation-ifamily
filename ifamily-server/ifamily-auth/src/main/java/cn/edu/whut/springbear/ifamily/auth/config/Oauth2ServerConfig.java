package cn.edu.whut.springbear.ifamily.auth.config;

import cn.edu.whut.springbear.ifamily.auth.pojo.dto.SecurityUser;
import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 15:03
 */
@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class Oauth2ServerConfig extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 后台管理
                .withClient(AuthConstants.CLIENT_ADMIN_ID)
                .secret(this.passwordEncoder.encode("ifamily-admin"))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token")
                // 管理员令牌有效期 1 天
                .accessTokenValiditySeconds(3600 * 24)
                .refreshTokenValiditySeconds(3600 * 24 * 7)
                .and()
                // 移动应用
                .withClient(AuthConstants.CLIENT_MOBILE_ID)
                .secret(this.passwordEncoder.encode("ifamily-mobile"))
                .scopes("all")
                .authorizedGrantTypes("password", "refresh_token")
                // 用户令牌有效期 3 天
                .accessTokenValiditySeconds(3600 * 24 * 3)
                .refreshTokenValiditySeconds(3600 * 24 * 7);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        List<TokenEnhancer> delegates = new ArrayList<>();
        delegates.add(this.tokenEnhancer());
        delegates.add(this.accessTokenConverter());
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(delegates);

        endpoints
                // 鉴权管理器
                .authenticationManager(this.authenticationManager)
                // 用户信息服务
                .userDetailsService(this.userDetailsService)
                // token 转换器
                .accessTokenConverter(this.accessTokenConverter())
                // token 增强器
                .tokenEnhancer(enhancerChain);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.allowFormAuthenticationForClients();
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(this.keyPair());
        return jwtAccessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        // 从 classpath 下的证书中获取秘钥对 [keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks]
        char[] rsaPassword = "ifamily".toCharArray();
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), rsaPassword);
        return keyStoreKeyFactory.getKeyPair("jwt", rsaPassword);
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            Map<String, Object> info = new HashMap<>(2);
            // 将 UID 设置到 token 中
            info.put("id", securityUser.getId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
            return accessToken;
        };
    }

}

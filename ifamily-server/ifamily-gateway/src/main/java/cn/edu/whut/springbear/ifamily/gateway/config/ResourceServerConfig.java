package cn.edu.whut.springbear.ifamily.gateway.config;

import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import cn.edu.whut.springbear.ifamily.gateway.component.AuthorizationManager;
import cn.edu.whut.springbear.ifamily.gateway.component.RestfulAccessDeniedHandler;
import cn.edu.whut.springbear.ifamily.gateway.component.RestfulAuthenticationEntryPoint;
import cn.edu.whut.springbear.ifamily.gateway.filter.WhitelistUrlsHandleFilter;
import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 16:11
 */
@AllArgsConstructor
@Configuration
@EnableWebFluxSecurity
public class ResourceServerConfig {

    private final AuthorizationManager authorizationManager;
    private final WhitelistUrlsConfig whitelistUrlsConfig;
    private final RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    private final RestfulAuthenticationEntryPoint restAuthenticationEntryPoint;
    private final WhitelistUrlsHandleFilter whitelistUrlsHandleFilter;

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        // 自定义 JWT 转换器
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(this.jwtAuthenticationConverter());
        // 自定义验证失败返回 Restful 结果
        http.oauth2ResourceServer().authenticationEntryPoint(this.restAuthenticationEntryPoint);
        // 自定义白名单路径移除请求头过滤器
        http.addFilterBefore(this.whitelistUrlsHandleFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        http.authorizeExchange()
                // 白名单配置
                .pathMatchers(ArrayUtil.toArray(this.whitelistUrlsConfig.getUrls(), String.class)).permitAll()
                // 鉴权管理器
                .anyExchange().access(this.authorizationManager)
                .and().exceptionHandling()
                // 处理未授权
                .accessDeniedHandler(this.restfulAccessDeniedHandler)
                // 处理未验证
                .authenticationEntryPoint(this.restAuthenticationEntryPoint)
                // 关闭跨域防护
                .and().csrf().disable();

        return http.build();
    }

    @Bean
    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix(AuthConstants.AUTHORITY_PREFIX);
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }

}

package cn.edu.whut.springbear.ifamily.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

import java.util.Arrays;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 16:10
 */
@Configuration
public class GlobalCorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // 配置允许访问的域名
        config.setAllowedOriginPatterns(Arrays.asList("http://localhost:8080", "http://10.128.184.117:8080"));
        // 允许所有请求方法跨域调用
        config.addAllowedMethod("*");
        // 放行全部原始头信息
        config.addAllowedHeader("*");
        // 允许跨越发送 Cookie
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }

}

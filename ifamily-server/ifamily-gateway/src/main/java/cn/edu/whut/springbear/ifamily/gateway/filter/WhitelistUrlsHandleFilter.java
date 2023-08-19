package cn.edu.whut.springbear.ifamily.gateway.filter;

import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import cn.edu.whut.springbear.ifamily.gateway.config.WhitelistUrlsConfig;
import lombok.AllArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 16:00
 */
@Component
@AllArgsConstructor
public class WhitelistUrlsHandleFilter implements WebFilter {

    private final WhitelistUrlsConfig whitelistUrlsConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String requestPath = request.getURI().getPath();
        PathMatcher pathMatcher = new AntPathMatcher();

        // 白名单请求路径移除 JWT 请求头
        List<String> ignoreUrls = this.whitelistUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, requestPath)) {
                request = exchange.getRequest().mutate().header(AuthConstants.JWT_TOKEN_HEADER, "").build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }

        return chain.filter(exchange);
    }

}

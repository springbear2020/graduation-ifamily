package cn.edu.whut.springbear.ifamily.gateway.filter;

import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import com.nimbusds.jose.JWSObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 15:55
 */
@Slf4j
@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst(AuthConstants.JWT_TOKEN_HEADER);
        if (!StringUtils.hasLength(token)) {
            return chain.filter(exchange);
        }
        token = token.replace(AuthConstants.JWT_TOKEN_PREFIX, "");

        try {
            // 从 token 中解析用户信息并设置到 Header 中去
            JWSObject jwsObject = JWSObject.parse(token);
            String userStr = jwsObject.getPayload().toString();
            log.info("AuthGlobalFilter.filter() general user: {}", userStr);
            ServerHttpRequest request = exchange.getRequest().mutate().header(AuthConstants.GENERAL_USER_HEADER, userStr).build();
            exchange = exchange.mutate().request(request).build();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }

}

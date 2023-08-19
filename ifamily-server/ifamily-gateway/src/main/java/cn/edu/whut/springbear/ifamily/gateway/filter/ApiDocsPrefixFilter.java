package cn.edu.whut.springbear.ifamily.gateway.filter;

import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.StripPrefixGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

/**
 * @author Spring-_-Bear
 * @since 23/05/05 09:37
 */
@Component
public class ApiDocsPrefixFilter extends StripPrefixGatewayFilterFactory {

    @Override
    public GatewayFilter apply(Config config) {
        final int targetIndex = config.getParts();

        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            String requestPath = request.getPath().value();
            String[] predicatePathArray = requestPath.split("/");

            /*
             * 当请求路径包含文档路径 AuthConstants.API_DOCS_PATH 时去除请求路径中的第一个路径，例子如下：
             * requestPath = "/acl/v2/api-docs" -> predicatePathArray = ["", "acl", "v2", "api-docs"] -> newPath = "/v2/api-docs"
             */
            if (predicatePathArray.length > targetIndex && requestPath.contains(AuthConstants.API_DOCS_PATH)) {
                StringBuilder newPath = new StringBuilder();
                // 由于 predicatePathArray 使用 / 切割后第一个元素为 ""，故 start = targetIndex + 1
                for (int start = targetIndex + 1; start < predicatePathArray.length; start++) {
                    newPath.append("/").append(predicatePathArray[start]);
                }

                request = request.mutate().path(newPath.toString()).build();
                exchange = exchange.mutate().request(request).build();
            }

            return chain.filter(exchange);
        };
    }

}


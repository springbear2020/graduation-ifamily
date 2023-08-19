package cn.edu.whut.springbear.ifamily.gateway.config;

import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.support.NameUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/13 17:48
 */
@Component
@Primary
@AllArgsConstructor
public class SwaggerResourceConfig implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;
    private final GatewayProperties gatewayProperties;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();

        // 获取所有路由的 ID
        List<String> routeIds = new ArrayList<>();
        routeLocator.getRoutes().subscribe(route -> routeIds.add(route.getId()));

        // 过滤出配置文件中定义的路由 -> 过滤出 Path Route Predicate -> 根据路径拼接成 api-docs 路径-> 生成 SwaggerResource
        gatewayProperties.getRoutes().stream()
                // 过滤出配置文件中定义的路由
                .filter(routeDefinition -> routeIds.contains(routeDefinition.getId()))
                .forEach(route -> route.getPredicates().stream()
                        // 过滤出每条路由 predicates 配置列表的 Path 集合
                        .filter(predicateDefinition -> ("Path").equalsIgnoreCase(predicateDefinition.getName()))
                        // 拼接每条路由 predicates 中 Path 路径列表以生成 SwaggerResource
                        .forEach(predicateDefinition -> {
                            // 例如：/auth/**
                            String predicatePath = predicateDefinition.getArgs().get(NameUtils.GENERATED_NAME_PREFIX + 0);
                            String apiDocsRequestUrl = predicatePath.replace("**", AuthConstants.API_DOCS_PATH);
                            resources.add(swaggerResource(route.getId(), apiDocsRequestUrl));
                        }));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }

}


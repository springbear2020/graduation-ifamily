package cn.edu.whut.springbear.ifamily.gateway.component;

import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import cn.edu.whut.springbear.ifamily.gateway.config.WhitelistUrlsConfig;
import cn.hutool.core.convert.Convert;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 16:16
 */
@Component
@AllArgsConstructor
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final RedisTemplate<String, Object> redisTemplate;
    private final WhitelistUrlsConfig whitelistUrlsConfig;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        // 跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        String requestPath = request.getURI().getPath();
        PathMatcher pathMatcher = new AntPathMatcher();
        List<String> ignoreUrls = this.whitelistUrlsConfig.getUrls();
        // 白名单请求路径直接放行
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, requestPath)) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }

        // 从 Redis 中获取 <权限路径, 权限对应的所有角色名称集合> Map
        Map<Object, Object> permissionRoleNamesMap = this.redisTemplate.opsForHash().entries(AuthConstants.PERMISSION_ROLES_MAP_KEY);
        if (permissionRoleNamesMap.isEmpty()) {
            return Mono.just(new AuthorizationDecision(true));
        }
        // 配置可访问当前路径的角色名称集合
        List<String> pathRoleNames = new ArrayList<>();
        Set<Map.Entry<Object, Object>> entries = permissionRoleNamesMap.entrySet();
        for (Map.Entry<Object, Object> entry : entries) {
            String path = (String) entry.getKey();
            if (pathMatcher.match(path, requestPath)) {
                pathRoleNames.addAll(Convert.toList(String.class, permissionRoleNamesMap.get(path)));
            }
        }
        // 将所有的角色名称加上 `ROLE_` 前缀
        pathRoleNames = pathRoleNames.stream().map(i -> i = AuthConstants.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        if (pathRoleNames.isEmpty()) {
            return Mono.just(new AuthorizationDecision(true));
        }

        /*
         * 返回一个 Mono<AuthorizationDecision> 对象，表示权限验证的结果。
         * 如果用户通过验证且其权限列表中至少有一个权限名称存在于 pathRoleNames 列表中，
         * 将返回一个具有 true 值的 AuthorizationDecision 实例；
         * 否则，将返回一个具有 false 值的 AuthorizationDecision 实例
         */
        return mono
                // 滤掉未经身份验证的用户
                .filter(Authentication::isAuthenticated)
                // 将身份验证对象中的权限列表拆分为单独的元素
                .flatMapIterable(Authentication::getAuthorities)
                // 将权限对象转换为权限名称
                .map(GrantedAuthority::getAuthority)
                // 检查权限名称是否存在于 pathRoleNames 列表中
                .any(pathRoleNames::contains)
                // 将 any() 布尔值结果转换为 AuthorizationDecision 实例
                .map(AuthorizationDecision::new)
                // 在 map() 结果为空时提供默认值
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}

package cn.edu.whut.springbear.ifamily.gateway.component;

import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.UserDTO;
import cn.edu.whut.springbear.ifamily.gateway.config.WhitelistUrlsConfig;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
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

import java.text.ParseException;
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

        // 从请求头中获取 token
        String token = request.getHeaders().getFirst(AuthConstants.JWT_TOKEN_HEADER);
        if (StrUtil.isEmpty(token)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        UserDTO userDTO;
        try {
            // 解析 token 获取用户信息
            JWSObject jwsObject = JWSObject.parse(token);
            String userStr = jwsObject.getPayload().toString();
            userDTO = JSONUtil.toBean(userStr, UserDTO.class);
        } catch (ParseException e) {
            return Mono.just(new AuthorizationDecision(false));
        }

        /* FIXME 不同用户体系登录不允许互相访问 */

        // 前台应用端请求管理端路径，禁止通行
        if (AuthConstants.CLIENT_MOBILE_ID.equals(userDTO.getClientId()) && pathMatcher.match(AuthConstants.ADMIN_URL_PATTERN, requestPath)) {
            return Mono.just(new AuthorizationDecision(false));
        }
        // 后台管理端请求非管理路径，禁止通行
        if (AuthConstants.CLIENT_ADMIN_ID.equals(userDTO.getClientId()) && !pathMatcher.match(AuthConstants.ADMIN_URL_PATTERN, requestPath)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        // 从 Redis 中获取所有权限路径对应的角色 Map。键为权限路径，值为角色名称集合
        Map<Object, Object> permissionRoleNamesMap = this.redisTemplate.opsForHash().entries(AuthConstants.PERMISSION_ROLES_MAP_KEY);
        if (permissionRoleNamesMap.isEmpty()) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 管理端路径需校验权限，配置访问当前请求路径所需的角色
        Set<Map.Entry<Object, Object>> entries = permissionRoleNamesMap.entrySet();
        List<String> needAuthorities = new ArrayList<>();
        for (Map.Entry<Object, Object> entry : entries) {
            String path = (String) entry.getKey();
            if (pathMatcher.match(path, requestPath)) {
                needAuthorities.addAll(Convert.toList(String.class, permissionRoleNamesMap.get(path)));
            }
        }
        // 将所有的角色名称加上 `ROLE_` 前缀
        needAuthorities = needAuthorities.stream().map(i -> i = AuthConstants.AUTHORITY_PREFIX + i).collect(Collectors.toList());
        if (needAuthorities.isEmpty()) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 检查用户拥有的角色和访问当前路径所需角色，认证通过且角色匹配可访问当前路径
        return mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(needAuthorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}

package cn.edu.whut.springbear.ifamily.security.config;


import cn.edu.whut.springbear.ifamily.security.access.DynamicPermissionProvider;
import cn.edu.whut.springbear.ifamily.security.access.RestfulForbiddenHandler;
import cn.edu.whut.springbear.ifamily.security.access.RestfulUnauthorizedEntryPoint;
import cn.edu.whut.springbear.ifamily.security.access.intercept.DynamicSecurityFilter;
import cn.edu.whut.springbear.ifamily.security.access.intercept.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/03/19 12:32
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private WhitelistResourcePathConfig whitelistResourcePathConfig;
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private RestfulForbiddenHandler restfulForbiddenHandler;
    @Autowired
    private RestfulUnauthorizedEntryPoint restfulUnauthorizedEntryPoint;
    @Autowired(required = false)
    private DynamicPermissionProvider dynamicSecurityService;
    @Autowired(required = false)
    private DynamicSecurityFilter dynamicSecurityFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 放行跨域的 OPTIONS 请求
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .and()
                // 任何请求需要身份认证
                .authorizeRequests().anyRequest().authenticated()
                .and()
                // 关闭跨站请求防护以及不使用 session
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 自定义权限拒绝处理类和认证错误处理类
                .exceptionHandling().accessDeniedHandler(this.restfulForbiddenHandler).authenticationEntryPoint(this.restfulUnauthorizedEntryPoint)
                .and()
                 // 自定义权限拦截器 JWT 过滤器，将其置于用户名密码验证过滤器之前，实现 token 有效时免用户认证
                .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // 有动态权限配置时添加动态权限校验过滤器
        if (this.dynamicSecurityService != null) {
            http.authorizeRequests().and().addFilterBefore(this.dynamicSecurityFilter, FilterSecurityInterceptor.class);
        }
    }

    @Override
    public void configure(WebSecurity web) {
        // 配置不需要权限控制的路径，直接放行
        List<String> urls = this.whitelistResourcePathConfig.getUrls();
        web.ignoring().antMatchers(urls.toArray(new String[0]));
    }

}

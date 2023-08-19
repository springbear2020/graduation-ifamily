package cn.edu.whut.springbear.ifamily.manager.config;

import cn.edu.whut.springbear.ifamily.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 17:02
 */
@Configuration
public class ManagerSecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userService.loadUserByUsername(username);
    }

}

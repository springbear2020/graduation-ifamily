package cn.edu.whut.springbear.ifamily.manager.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 17:47
 */
public interface UserService {

    /**
     * 根据用户名查询用户信息，将查询到的用户信息 UserDO 封装为 SecurityUserDetailsDTO，
     * 以供 SpringSecurity 安全框架鉴权使用
     *
     * @return org.springframework.security.core.userdetails.UserDetails
     */
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}

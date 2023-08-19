package cn.edu.whut.springbear.ifamily.security.util;


import org.junit.jupiter.api.Test;

/**
 * @author Spring-_-Bear
 * @since 23/03/20 08:41
 */
class JwtUtilsTest {

    @Test
    void create() {
        String token = JwtUtils.create("username", "Spring-_-Bear");
        System.out.println(token);
        System.out.println(JwtUtils.get(token, "username"));
        System.out.println(JwtUtils.isNonExpired(token));
    }

}
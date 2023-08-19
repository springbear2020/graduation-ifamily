package cn.edu.whut.springbear.ifamily.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Spring-_-Bear
 * @since 23/03/25 17:42
 */
@SpringBootTest
class UsernameUpdateLogServiceTest {

    @Autowired
    private UsernameUpdateLogService usernameUpdateLogService;

    @Test
    void getLatest() {
        System.out.println(usernameUpdateLogService.getLatestOfUser(1L));
    }

}
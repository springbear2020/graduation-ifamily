package cn.edu.whut.springbear.ifamily.user.service.impl;

import cn.edu.whut.springbear.ifamily.user.service.UsernameUpdateLogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Spring-_-Bear
 * @since 23/03/25 17:42
 */
@SpringBootTest
class UsernameUpdateLogServiceImplTest {

    @Autowired
    private UsernameUpdateLogService usernameUpdateLogService;

    @Test
    void getLatest() {
        System.out.println(usernameUpdateLogService.getLatest(1L));
    }

}
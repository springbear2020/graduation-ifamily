package cn.edu.whut.springbear.ifamily.acl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 21:55
 */
@SpringBootTest
class UserRoleServiceTest {

    @Autowired
    private UserRoleService userRoleService;

    @Test
    void listRoleIdsOfUser() {
        userRoleService.listRoleIdsOfUser(20L).forEach(System.out::println);
    }

}
package cn.edu.whut.springbear.ifamily.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Spring-_-Bear
 * @since 23/03/20 10:21
 */
@SpringBootTest
class PermissionServiceTest {

    @Autowired
    private PermissionService permissionService;

    @Test
    void listPermissionsOfUser() {
        permissionService.listPermissionsOfUser(20L).forEach(System.out::println);
    }

    @Test
    void listAll() {
        permissionService.listAll().forEach(System.out::println);
    }

}
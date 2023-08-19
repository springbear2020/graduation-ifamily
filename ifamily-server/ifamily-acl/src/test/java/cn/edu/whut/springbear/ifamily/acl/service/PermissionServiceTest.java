package cn.edu.whut.springbear.ifamily.acl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 21:54
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
        permissionService.list().forEach(System.out::println);
    }

}
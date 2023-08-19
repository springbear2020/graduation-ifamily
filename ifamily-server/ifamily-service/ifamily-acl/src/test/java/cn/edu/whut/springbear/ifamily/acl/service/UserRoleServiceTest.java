package cn.edu.whut.springbear.ifamily.acl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 22:55
 */
@SpringBootTest
class UserRoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Test
    void listRoleNamesOfUser() {
        System.out.println(roleService.listRoleNamesOfUser(1L));
    }

}
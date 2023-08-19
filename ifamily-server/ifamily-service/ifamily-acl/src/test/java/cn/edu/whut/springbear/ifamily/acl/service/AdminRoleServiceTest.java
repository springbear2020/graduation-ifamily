package cn.edu.whut.springbear.ifamily.acl.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 21:12
 */
@SpringBootTest
class AdminRoleServiceTest {

    @Autowired
    private AdminRoleService adminRoleService;

    @Test
    void listRoleNamesOfAdmin() {
        System.out.println(adminRoleService.listRoleNamesOfAdmin(1L));
    }

}
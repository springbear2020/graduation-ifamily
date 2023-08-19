package cn.edu.whut.springbear.ifamily.admin.service;

import cn.edu.whut.springbear.ifamily.admin.pojo.po.AdminDO;
import cn.hutool.crypto.digest.BCrypt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/04/13 09:39
 */
@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Test
    void create() {
        AdminDO userDO = new AdminDO();
        userDO.setId(1L);
        userDO.setUsername("admin");
        userDO.setPassword(BCrypt.hashpw("admin"));
        userDO.setNickname("Spring-_-Bear");
        userDO.setAvatar("");
        userDO.setStatus(0);
        Date date = new Date();
        userDO.setLastLogin(date);
        userDO.setCreated(date);
        userDO.setModified(date);
        userDO.setDeleted(0);
        System.out.println(this.adminService.save(userDO));
    }

}
package cn.edu.whut.springbear.ifamily.backend.service;

import cn.edu.whut.springbear.ifamily.backend.pojo.po.AdminDO;
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
        userDO.setUsername("admin");
        userDO.setPassword(BCrypt.hashpw("admin"));
        userDO.setNickname("Spring-_-Bear");
        userDO.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        userDO.setSignature("路漫漫其修远兮，吾将上下而求索。");
        userDO.setStatus(0);
        Date date = new Date();
        userDO.setLastLogin(date);
        userDO.setCreated(date);
        userDO.setModified(date);
        userDO.setDeleted(0);
        this.adminService.save(userDO);
    }

}
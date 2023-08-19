package cn.edu.whut.springbear.ifamily.user.service;

import cn.edu.whut.springbear.ifamily.user.pojo.po.UserDO;
import cn.hutool.crypto.digest.BCrypt;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author Spring-_-Bear
 * @since 23/04/13 09:35
 */
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void create() {
        UserDO userDO = new UserDO();
        userDO.setId(1L);
        userDO.setUsername("bear");
        userDO.setPassword(BCrypt.hashpw("bear"));
        userDO.setPhone("13568945862");
        userDO.setEmail("springbear@163.com");
        userDO.setNickname("Spring-_-Bear");
        userDO.setAvatar("");
        userDO.setSignature("路漫漫其修远兮，吾将上下而求索。");
        userDO.setStatus(0);
        Date date = new Date();
        userDO.setLastLogin(date);
        userDO.setCreated(date);
        userDO.setModified(date);
        userDO.setDeleted(0);
        System.out.println(this.userService.save(userDO));
    }

}
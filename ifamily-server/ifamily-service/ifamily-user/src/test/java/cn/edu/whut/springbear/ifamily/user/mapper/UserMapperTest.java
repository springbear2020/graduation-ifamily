package cn.edu.whut.springbear.ifamily.user.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Spring-_-Bear
 * @since 23/04/14 17:16
 */
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void selectByColumn() {
        System.out.println(this.userMapper.selectByColumn("email", "springbear2020@163.com"));
    }

}
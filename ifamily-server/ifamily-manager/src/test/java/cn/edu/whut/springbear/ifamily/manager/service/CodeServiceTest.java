package cn.edu.whut.springbear.ifamily.manager.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Spring-_-Bear
 * @since 23/03/23 10:26
 */
@SpringBootTest
class CodeServiceTest {

    @Autowired
    private CodeService codeService;

    @Test
    void sendEmailCode() {
        System.out.println(codeService.sendEmailCode("springbear2020@163.com"));
    }

}
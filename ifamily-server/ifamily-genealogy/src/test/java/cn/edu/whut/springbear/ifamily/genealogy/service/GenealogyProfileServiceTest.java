package cn.edu.whut.springbear.ifamily.genealogy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;


/**
 * @author Spring-_-Bear
 * @since 23/04/04 10:56
 */
@SpringBootTest
class GenealogyProfileServiceTest {

    @Test
    void genealogyPeopleIncreaseOne(@Autowired GenealogyProfileService genealogyProfileService) {
        System.out.println(genealogyProfileService.increaseOne(9L, 1, new Date()));
    }

}
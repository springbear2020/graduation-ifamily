package cn.edu.whut.springbear.ifamily.genealogy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 11:30
 */
@SpringBootTest
class UserGenealogyServiceTest {

    @Autowired
    private UserGenealogyService userGenealogyService;

    @Test
    void listGenealogyIdsOfUser() {
        userGenealogyService.listGenealogiesOfUser(10L).forEach(System.out::println);
    }

    @Test
    void getUserDefaultGenealogy() {
        System.out.println(this.userGenealogyService.getDefault(10L));
    }

}
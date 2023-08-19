package cn.edu.whut.springbear.ifamily.genealogy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Spring-_-Bear
 * @since 23/03/29 11:30
 */
@SpringBootTest
class GenealogyUserServiceTest {

    @Autowired
    private GenealogyUserService genealogyUserService;

    @Test
    void listGenealogyIdsOfUser() {
        genealogyUserService.listGenealogiesOfUser(10L).forEach(System.out::println);
    }

    @Test
    void getUserDefaultGenealogy() {
        System.out.println(this.genealogyUserService.getDefaultGenealogyOfUser(10L));
    }

}
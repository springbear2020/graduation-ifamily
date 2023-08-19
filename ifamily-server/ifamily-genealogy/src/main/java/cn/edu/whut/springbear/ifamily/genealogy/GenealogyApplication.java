package cn.edu.whut.springbear.ifamily.genealogy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Spring-_-Bear
 * @since 23/03/28 16:10
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("cn.edu.whut.springbear.ifamily.client")
@ComponentScan("cn.edu.whut.springbear.ifamily")
public class GenealogyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenealogyApplication.class, args);
    }

}

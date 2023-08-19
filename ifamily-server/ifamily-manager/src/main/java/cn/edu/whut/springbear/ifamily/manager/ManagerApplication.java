package cn.edu.whut.springbear.ifamily.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Spring-_-Bear
 * @since 23/03/23 08:53
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("cn.edu.whut.springbear.ifamily")
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }

}

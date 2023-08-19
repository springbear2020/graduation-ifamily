package cn.edu.whut.springbear.ifamily.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Spring-_-Bear
 * @since 23/03/10 21:56
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("cn.edu.whut.springbear.ifamily")
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}

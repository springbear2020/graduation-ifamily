package cn.edu.whut.springbear.ifamily.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Spring-_-Bear
 * @since 23/03/10 21:56
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("cn.edu.whut.springbear.ifamily")
public class BackandApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackandApplication.class, args);
    }

}

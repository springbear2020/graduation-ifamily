package cn.edu.whut.springbear.ifamily.acl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Spring-_-Bear
 * @since 23/03/26 21:49
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("cn.edu.whut.springbear.ifamily")
public class AclApplication {

    public static void main(String[] args) {
        SpringApplication.run(AclApplication.class, args);
    }

}

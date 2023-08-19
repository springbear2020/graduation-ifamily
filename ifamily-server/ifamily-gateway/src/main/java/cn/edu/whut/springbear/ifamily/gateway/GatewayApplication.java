package cn.edu.whut.springbear.ifamily.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 16:06
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("cn.edu.whut.springbear.ifamily")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}

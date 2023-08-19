package cn.edu.whut.springbear.ifamily.manager.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Spring-_-Bear
 * @since 23/03/23 10:50
 */
@Configuration
@MapperScan("cn.edu.whut.springbear.ifamily.manager.mapper")
public class MyBatisPlusConfig {
}

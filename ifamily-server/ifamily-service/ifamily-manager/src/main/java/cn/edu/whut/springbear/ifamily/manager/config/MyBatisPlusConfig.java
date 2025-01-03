package cn.edu.whut.springbear.ifamily.manager.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 16:23
 */
@Configuration
@MapperScan("cn.edu.whut.springbear.ifamily.manager.mapper")
public class MyBatisPlusConfig {
}

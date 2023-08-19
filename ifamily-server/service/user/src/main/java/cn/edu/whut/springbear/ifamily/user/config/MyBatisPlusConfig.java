package cn.edu.whut.springbear.ifamily.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Spring-_-Bear
 * @since 23/03/11 10:59
 */
@Configuration
@MapperScan("cn.edu.whut.springbear.ifamily.user.mapper")
public class MyBatisPlusConfig {
}

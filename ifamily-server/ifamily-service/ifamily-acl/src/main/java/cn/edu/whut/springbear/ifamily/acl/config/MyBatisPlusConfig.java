package cn.edu.whut.springbear.ifamily.acl.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Spring-_-Bear
 * @since 23/04/12 16:23
 */
@EnableTransactionManagement
@Configuration
@MapperScan("cn.edu.whut.springbear.ifamily.acl.mapper")
public class MyBatisPlusConfig {
}

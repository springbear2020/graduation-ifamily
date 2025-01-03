package cn.edu.whut.springbear.ifamily.backend.config;

import cn.edu.whut.springbear.ifamily.common.config.BaseSwaggerConfig;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.SwaggerDTO;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Spring-_-Bear
 * @since 23/04/13 20:03
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerDTO swaggerInfo() {
        return SwaggerDTO.builder()
                .apiBasePackage("cn.edu.whut.springbear.ifamily.backend.controller")
                .enableSecurity(true)
                .title("百家谱管理员模块")
                .description("百家谱管理员服务中心相关接口文档")
                .version("1.0")
                .contactName("Spring-_-Bear")
                .contactUrl("https://github.com/springbear2020")
                .contactEmail("springbear2020@163.com")
                .build();
    }

    @Bean
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return generateBeanPostProcessor();
    }

}

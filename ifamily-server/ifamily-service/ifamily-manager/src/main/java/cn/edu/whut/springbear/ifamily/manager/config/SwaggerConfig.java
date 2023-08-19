package cn.edu.whut.springbear.ifamily.manager.config;

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
                .apiBasePackage("cn.edu.whut.springbear.ifamily.manager.controller")
                .enableSecurity(true)
                .title("百家谱第三方服务中心")
                .description("百家谱第三方服务中心相关接口文档")
                .version("1.0")
                .contactName("Spring-_-Bear")
                .contactUrl("https://springbear.blog.csdn.net")
                .contactEmail("springbear2020@163.com")
                .build();
    }

    @Bean
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return generateBeanPostProcessor();
    }

}
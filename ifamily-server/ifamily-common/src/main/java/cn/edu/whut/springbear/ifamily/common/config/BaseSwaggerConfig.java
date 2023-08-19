package cn.edu.whut.springbear.ifamily.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger 基础配置
 *
 * @author Spring-_-Bear
 * @since 23/03/11 09:56
 */
@Configuration
@EnableSwagger2
public class BaseSwaggerConfig {

    @Bean
    public Docket restApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("ifamily")
                .apiInfo(apiInfo())
                .select()
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("百家谱 - API 文档")
                .description("本文档描述了百家谱网站微服务接口定义")
                .version("1.0")
                .contact(new Contact("Spring-_-Bear", "https://springbear.blog.csdn.net", "springbear2020@163.com"))
                .build();
    }

}

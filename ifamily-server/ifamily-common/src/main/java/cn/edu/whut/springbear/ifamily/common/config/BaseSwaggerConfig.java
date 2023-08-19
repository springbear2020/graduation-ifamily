package cn.edu.whut.springbear.ifamily.common.config;

import cn.edu.whut.springbear.ifamily.common.constant.AuthConstants;
import cn.edu.whut.springbear.ifamily.common.pojo.dto.SwaggerDTO;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Spring-_-Bear
 * @since 23/04/13 19:48
 */
@SuppressWarnings("all")
public abstract class BaseSwaggerConfig {

    /**
     * 自定义 Swagger 配置
     */
    protected abstract SwaggerDTO swaggerInfo();

    /**
     * 生成 Swagger 文档
     */
    @Bean
    public Docket createRestApi() {
        // 读取子类自定义的 Swagger 配置信息
        SwaggerDTO swaggerDTO = this.swaggerInfo();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo(swaggerDTO))
                .select()
                .apis(RequestHandlerSelectors.basePackage(swaggerDTO.getApiBasePackage()))
                .paths(PathSelectors.any())
                .build();

        // 是否要启用登录认证
        if (swaggerDTO.getEnableSecurity()) {
            docket.securitySchemes(this.securitySchemes()).securityContexts(this.securityContexts());
        }

        return docket;
    }

    /**
     * 设置文档信息
     * Used by cn.edu.whut.springbear.ifamily.common.config.BaseSwaggerConfig#createRestApi()
     */
    private ApiInfo apiInfo(SwaggerDTO swaggerDTO) {
        return new ApiInfoBuilder()
                .title(swaggerDTO.getTitle())
                .description(swaggerDTO.getDescription())
                .contact(new Contact(swaggerDTO.getContactName(), swaggerDTO.getContactUrl(), swaggerDTO.getContactEmail()))
                .version(swaggerDTO.getVersion())
                .build();
    }

    /**
     * 设置请求头信息
     * Used by cn.edu.whut.springbear.ifamily.common.config.BaseSwaggerConfig#createRestApi()
     */
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> result = new ArrayList<>();
        ApiKey apiKey = new ApiKey(AuthConstants.JWT_TOKEN_HEADER, AuthConstants.JWT_TOKEN_HEADER, "header");
        result.add(apiKey);
        return result;
    }

    /**
     * 设置需要登录认证的路径
     * Used by cn.edu.whut.springbear.ifamily.common.config.BaseSwaggerConfig#createRestApi()
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> result = new ArrayList<>();
        result.add(SecurityContext.builder()
                .securityReferences(this.defaultAuth())
                .forPaths(PathSelectors.regex("/*/.*"))
                .build());
        return result;
    }

    /**
     * 设置默认认证规则
     * Used by cn.edu.whut.springbear.ifamily.common.config.BaseSwaggerConfig#securityContexts()
     */
    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> result = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        result.add(new SecurityReference(AuthConstants.JWT_TOKEN_HEADER, authorizationScopes));
        return result;
    }

    /**
     * 实例后置处理器
     * Used by subclass
     */
    public BeanPostProcessor generateBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
                if (bean instanceof WebMvcRequestHandlerProvider || bean instanceof WebFluxRequestHandlerProvider) {
                    customizeSpringfoxHandlerMappings(getHandlerMappings(bean));
                }
                return bean;
            }

            private <T extends RequestMappingInfoHandlerMapping> void customizeSpringfoxHandlerMappings(List<T> mappings) {
                List<T> copy = mappings.stream()
                        .filter(mapping -> mapping.getPatternParser() == null)
                        .collect(Collectors.toList());
                mappings.clear();
                mappings.addAll(copy);
            }

            private List<RequestMappingInfoHandlerMapping> getHandlerMappings(Object bean) {
                try {
                    Field field = ReflectionUtils.findField(bean.getClass(), "handlerMappings");
                    assert field != null;
                    field.setAccessible(true);
                    return (List<RequestMappingInfoHandlerMapping>) field.get(bean);
                } catch (IllegalArgumentException | IllegalAccessException e) {
                    throw new IllegalStateException(e);
                }
            }

        };
    }

}

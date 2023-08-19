package cn.edu.whut.springbear.ifamily.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.*;

import java.util.Optional;

/**
 * @author Spring-_-Bear
 * @since 23/04/13 17:52
 */
@RestController
public class SwaggerHandler {

    private final SwaggerResourcesProvider swaggerResources;

    @Autowired
    public SwaggerHandler(SwaggerResourcesProvider swaggerResources) {
        this.swaggerResources = swaggerResources;
    }

    /**
     * Swagger 安全配置，支持 oauth 和 apiKey 设置
     */
    @GetMapping("/swagger-resources/configuration/security")
    public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration(SecurityConfiguration securityConfiguration) {
        return Mono.just(new ResponseEntity<>(Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    /**
     * Swagger UI 配置
     */
    @GetMapping("/swagger-resources/configuration/ui")
    public Mono<ResponseEntity<UiConfiguration>> uiConfiguration(UiConfiguration uiConfiguration) {
        return Mono.just(new ResponseEntity<>(Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
    }

    /**
     * Swagger 资源配置，即配置微服务中各个服务的 api-docs 信息
     */
    @GetMapping("/swagger-resources")
    public Mono<ResponseEntity<Object>> swaggerResources() {
        return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
    }

}

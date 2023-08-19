package cn.edu.whut.springbear.ifamily.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Spring-_-Bear
 * @since 23/04/11 16:01
 */
@Data
@Component
@ConfigurationProperties(prefix = "secure.ignore")
public class WhitelistUrlsConfig {

    private List<String> urls;

}

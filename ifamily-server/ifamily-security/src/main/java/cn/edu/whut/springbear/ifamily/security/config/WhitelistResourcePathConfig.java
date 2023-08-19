package cn.edu.whut.springbear.ifamily.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 白名单资源路径配置
 *
 * @author Spring-_-Bear
 * @since 23/03/19 11:31
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ifamily.security.ignored")
public class WhitelistResourcePathConfig {

    private List<String> urls = new ArrayList<>();

}

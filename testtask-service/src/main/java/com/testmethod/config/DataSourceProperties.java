package com.testmethod.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * @author Denis Yakovenko
 */
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "data-sources")
@PropertySource(value = "classpath:ds.yaml", factory = YamlPropertySourceFactory.class)
public class DataSourceProperties {
    private List<DataSourceConfig> servers;
}

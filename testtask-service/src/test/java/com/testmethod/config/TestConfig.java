package com.testmethod.config;

import com.testmethod.service.DataSourceStrategy;
import com.testmethod.service.MySqlDataSourceStrategy;
import com.testmethod.service.PostgresDataSourceStrategy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Denis Yakovenko
 */
@TestConfiguration
public class TestConfig {
    @Bean
    public List<DataSourceStrategy> dataSourceStrategyList() {
        List<DataSourceStrategy> a = new ArrayList<>();
        a.add(new PostgresDataSourceStrategy());
        a.add(new MySqlDataSourceStrategy());
        return a;
    }
}

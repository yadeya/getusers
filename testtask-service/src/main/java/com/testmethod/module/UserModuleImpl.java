package com.testmethod.module;

import com.testmethod.config.*;
import com.testmethod.models.User;
import com.testmethod.service.DataSourceStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author Denis Yakovenko
 */
@Component
@Slf4j
@EnableConfigurationProperties(DataSourceProperties.class)
public class UserModuleImpl implements UserModule {
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Autowired
    private List<DataSourceStrategy> dataSourceStrategyList;

    private static final List<DatabaseTask> tasks = new ArrayList<>();

    @Override
    public List<User> getUsers() {
        log.info("getUsers() invoked");

        for (DataSourceConfig dataSourceConfig : dataSourceProperties.getServers()) {

            Map<String, String> mapping = new HashMap<>();
            mapping.put("id", dataSourceConfig.getMapping().getId());
            mapping.put("username", dataSourceConfig.getMapping().getUsername());
            mapping.put("name", dataSourceConfig.getMapping().getName());
            mapping.put("surname", dataSourceConfig.getMapping().getSurname());

            for (DataSourceStrategy strategy : dataSourceStrategyList) {
                if (strategy.getType().equalsIgnoreCase(dataSourceConfig.getStrategy())) {
                    DataSource dataSource = strategy.getDataSource(dataSourceConfig.getUrl(), dataSourceConfig.getUser(), dataSourceConfig.getPassword());
                    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
                    tasks.add(new DatabaseTask(jdbcTemplate, dataSourceConfig.getTable(), mapping));
                }
            }
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<List<User>>> results = new ArrayList<>();
        List<User> response = new ArrayList<>();

        for (DatabaseTask databaseTask : tasks) {
            results.add(executor.submit(databaseTask));
        }

        for (Future<List<User>> result : results) {
            List<User> data;
            try {
                data = result.get();
                response.addAll(data);
            } catch (InterruptedException | ExecutionException e) {
                // todo choose
                // throw new ApiException(404, "Error/Not found response");
                // nothing to do, suppress to skip dead endpoints
            }
        }
        executor.shutdown();
        // todo sort, add pagination or so.
        log.info("getUsers() completed with result size " + response.size());
        return response;
    }

    static class DatabaseTask implements Callable<List<User>> {
        private final JdbcTemplate jdbcTemplate;
        private final String tableName;
        private final Map<String, String> mapping;

        public DatabaseTask(JdbcTemplate jdbcTemplate, String tableName, Map<String, String> mapping) {
            this.jdbcTemplate = jdbcTemplate;
            this.tableName = tableName;
            this.mapping = new HashMap<>();
            this.mapping.putAll(mapping);
        }

        @Override
        public List<User> call() {
            String sql = "select * from " + tableName;
            List<User> users = jdbcTemplate.query(sql, new UserRowMapper(mapping));
            log.info("users size: " + users.size());
            return users;
        }
    }
}


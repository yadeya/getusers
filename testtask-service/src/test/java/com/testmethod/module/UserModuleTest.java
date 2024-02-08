package com.testmethod.module;

import com.testmethod.config.DataSourceConfig;
import com.testmethod.config.DataSourceProperties;
import com.testmethod.config.MappingConfig;
import com.testmethod.config.TestConfig;
import com.testmethod.models.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author Denis Yakovenko
 */

@SpringBootTest
@Import(TestConfig.class)
@Testcontainers
@ContextConfiguration(classes = {UserModuleImpl.class})
public class UserModuleTest {
    @MockBean
    private DataSourceProperties dataSourceProperties;
    @Autowired
    private UserModule userModule;
    @Container
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest");

    @BeforeAll
    public static void setUp() {
        postgresqlContainer.start();
        try (Connection connection = DriverManager.getConnection(postgresqlContainer.getJdbcUrl(),
                postgresqlContainer.getUsername(), postgresqlContainer.getPassword())) {
            try (Statement statement = connection.createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS users (id VARCHAR(255) PRIMARY KEY, name VARCHAR(255))");
            }
            try (Statement statement = connection.createStatement()) {
                statement.execute("INSERT INTO users (id, name) VALUES ('1', 'John')");
                statement.execute("INSERT INTO users (id, name) VALUES ('2', 'Alice')");
            }
        } catch (SQLException e) {
            Assertions.fail();
        }
    }

    @AfterAll
    public static void tearDown() {
        postgresqlContainer.stop();
    }

    @Test
    void testGetUsersOK() throws Exception {
        List<DataSourceConfig> servers = new ArrayList<>();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(postgresqlContainer.getJdbcUrl());
        dataSourceConfig.setUser(postgresqlContainer.getUsername());
        dataSourceConfig.setPassword(postgresqlContainer.getPassword());
        dataSourceConfig.setTable("users");
        dataSourceConfig.setStrategy("postgres");
        MappingConfig mappingConfig = new MappingConfig("id", null, "name", null);
        dataSourceConfig.setMapping(mappingConfig);
        servers.add(dataSourceConfig);

        when(dataSourceProperties.getServers()).thenReturn(servers);

        List<User> users = userModule.getUsers();
        assertEquals(2, users.size());

        for (User user : users) {
            assertNotNull(user.getId());
            assertNotNull(user.getName());
        }
    }
}

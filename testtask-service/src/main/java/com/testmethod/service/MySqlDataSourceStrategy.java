package com.testmethod.service;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * @author Denis Yakovenko
 */
@Service
public class MySqlDataSourceStrategy implements DataSourceStrategy {

    public static final String MYSQL = "mysql";

    @Override
    public String getType() {
        return MYSQL;
    }

    @Override
    public DataSource getDataSource(String url, String user, String password) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}
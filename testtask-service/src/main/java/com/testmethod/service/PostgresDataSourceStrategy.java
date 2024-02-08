package com.testmethod.service;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;

/**
 * @author Denis Yakovenko
 */
@Service
public class PostgresDataSourceStrategy implements DataSourceStrategy {

    public static final String POSTGRES = "postgres";

    @Override
    public String getType() {
        return POSTGRES;
    }

    @Override
    public DataSource getDataSource(String url, String user, String password) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl(url);
        dataSource.setUser(user);
        dataSource.setPassword(password);
        return dataSource;
    }
}

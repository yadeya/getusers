package com.testmethod.service;

import javax.sql.DataSource;

/**
 * @author Denis Yakovenko
 */
public interface DataSourceStrategy {
    String getType();
    DataSource getDataSource(String url, String user, String password);
}

package com.testmethod.config;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Denis Yakovenko
 */
@Setter
@Getter
public
class DataSourceConfig {
    private String name;
    private String strategy;
    private String url;
    private String table;
    private String user;
    private String password;
    private MappingConfig mapping = new MappingConfig();
}

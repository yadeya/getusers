package com.testmethod.module;

import com.testmethod.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Denis Yakovenko
 */
public class UserRowMapper implements RowMapper<User> {
    private final Map<String,String> mapping;

    public UserRowMapper(Map<String,String> mapping) {
        this.mapping = new HashMap<>();
        this.mapping.putAll(mapping);
    }

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        if (mapping.get("id") != null) {
            user.setId(resultSet.getString(mapping.get("id")));
        }
        if (mapping.get("userName") != null) {
            user.setUsername(resultSet.getString(mapping.get("userName")));
        }
        if (mapping.get("surname") != null) {
            user.setSurname(resultSet.getString(mapping.get("surname")));
        }
        if (mapping.get("name") != null) {
            user.setName(resultSet.getString(mapping.get("name")));
        }
        return user;
    }
}

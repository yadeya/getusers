package com.testmethod.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Denis Yakovenko
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MappingConfig {
    private String id;
    private String username;
    private String name;
    private String surname;
}

package com.testmethod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = { "com.testmethod", "com.testmethod.service" , "com.testmethod.config"})
public class TestTaskApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestTaskApplication.class, args);
	}
}

package com.sgutsev.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.ResourceBundle;

public class SpringConfig {
    private static final ResourceBundle resource = ResourceBundle.getBundle("application");

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(resource.getString("spring.datasource.driver-class-name"));
        dataSource.setUrl(resource.getString("spring.datasource.url"));
        dataSource.setUsername(resource.getString("spring.datasource.username"));
        dataSource.setPassword(resource.getString("spring.datasource.password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}

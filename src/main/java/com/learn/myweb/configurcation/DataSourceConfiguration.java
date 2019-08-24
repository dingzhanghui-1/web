package com.learn.myweb.configurcation;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;


    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String dirver;


    @Value("${spring.datasource.max-idle}")
    private int max_idle;

    @Value("${spring.datasource.max-wait}")
    private long max_wait;

    @Value("${spring.datasource.max-idle}")
    private int min_idle;

    @Value("${spring.datasource.initial-size}")
    private int initial_size;

    @Bean
    public DataSource GetDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(dirver);
        dataSource.setMinimumIdle(min_idle);
        dataSource.setConnectionTimeout(max_wait);
        dataSource.setMaximumPoolSize(max_idle);
        dataSource.setConnectionInitSql("set names utf8mb4;");
        return dataSource;
    }
}

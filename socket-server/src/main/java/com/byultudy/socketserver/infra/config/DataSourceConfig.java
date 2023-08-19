package com.byultudy.socketserver.infra.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@ConditionalOnBean(name = "hibernateJpaAutoConfiguration")
@Configuration
public class DataSourceConfig {

    public static final String DATASOURCE_HIKARI_PREFIX = "spring.datasource.hikari";

    @ConditionalOnMissingBean(name = "defaultDataSource", value = DataSource.class)
    @Bean(name = {"defaultDataSource", "dataSource"})
    @ConfigurationProperties(DATASOURCE_HIKARI_PREFIX)
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .build();
    }

}
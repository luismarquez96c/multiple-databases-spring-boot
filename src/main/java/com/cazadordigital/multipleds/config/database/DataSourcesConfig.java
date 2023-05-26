package com.cazadordigital.multipleds.config.database;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourcesConfig {

    @Primary
    @Bean("mysqlProperties")
    @ConfigurationProperties(prefix = "spring.datasource.mysql")
    public DataSourceProperties getMysqlProperties(){
        return new DataSourceProperties();
    }

    @Primary
    @Bean("mysqlDatasource")
    public DataSource getMysqlDatasource(){
        DataSourceProperties mysqlProps = getMysqlProperties();
        return mysqlProps.initializeDataSourceBuilder()
                .build();
    }

    @Bean("postgresqlProperties")
    @ConfigurationProperties(prefix = "spring.datasource.postgresql")
    public DataSourceProperties getPostgresqlProperties(){
        return new DataSourceProperties();
    }

    @Bean("postgresqlDatasource")
    public DataSource getPostgesqlDatasource(){
        DataSourceProperties postgresqlProps = getPostgresqlProperties();
        return postgresqlProps.initializeDataSourceBuilder()
                .build();
    }

}

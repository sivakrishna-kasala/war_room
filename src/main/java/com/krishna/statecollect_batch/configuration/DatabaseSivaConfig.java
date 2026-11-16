package com.krishna.statecollect_batch.configuration;


import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseSivaConfig {


    @Bean("secondDatasource")
    @ConfigurationProperties(prefix = "database-siva.datasource")
    DataSource secondDatasource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("secondHikariDatasource")
    @ConfigurationProperties(prefix = "database-siva.datasource")
    HikariDataSource secondHikariDatasource(){
        return new HikariDataSource();
    }

    @Bean
    NamedParameterJdbcTemplate databaseSivaTemplate(@Qualifier("secondHikariDatasource") HikariDataSource secondHikariDatasource){
        return new NamedParameterJdbcTemplate(secondHikariDatasource);
    }
}

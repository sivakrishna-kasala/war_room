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
public class NamedParameterJdbcTemplateConfig {

    @Bean("firstDatasource")
    @ConfigurationProperties(prefix = "database-original.datasource")
    DataSource firstDatasource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("firstHikariDatasource")
    @ConfigurationProperties(prefix="database-original.datasource")
    HikariDataSource firstHikariDatasource(){
        return new HikariDataSource();
    }

    @Bean("actorTemplate")
    NamedParameterJdbcTemplate actorTemplate(@Qualifier("firstHikariDatasource") HikariDataSource firstHikariDatasource){
        return new NamedParameterJdbcTemplate(firstHikariDatasource);
    }

}

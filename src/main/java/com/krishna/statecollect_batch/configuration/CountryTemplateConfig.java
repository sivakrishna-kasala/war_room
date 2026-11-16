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
public class CountryTemplateConfig {


    @Bean("countryDataSource")
    @ConfigurationProperties(prefix = "database-country.datasource")
    DataSource countryDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean("countryHikariDataSource")
    @ConfigurationProperties(prefix = "database-country.datasource")
    HikariDataSource countryHikariDataSource(){
        return new HikariDataSource();
    }

    @Bean("countryTemplate")
    NamedParameterJdbcTemplate countryTemplate(@Qualifier("countryHikariDataSource") HikariDataSource source){
        return new NamedParameterJdbcTemplate(source);
    }

}

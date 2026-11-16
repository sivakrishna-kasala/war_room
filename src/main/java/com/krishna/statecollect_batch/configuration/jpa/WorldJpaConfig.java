package com.krishna.statecollect_batch.configuration.jpa;


import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;

import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.krishna.statecollect_batch.repository",
        entityManagerFactoryRef = "secondEntityManagerFactory",
        transactionManagerRef = "secondTransactionManager"
)
public class WorldJpaConfig {

    @Bean("jpaDataSource")
    @ConfigurationProperties(prefix = "database-company.datasource")
    public DataSource jpaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean("secondEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondEntityManagerFactory(
//            EntityManagerFactoryBuilder builder,
            @Qualifier("jpaDataSource") DataSource dataSource) {

        LocalContainerEntityManagerFactoryBean factory =
                new LocalContainerEntityManagerFactoryBean();

        factory.setDataSource(dataSource);

        factory.setPackagesToScan(
                "com.krishna.statecollect_batch.entity"
        );

        factory.setPersistenceUnitName("second");

        factory.setJpaVendorAdapter(
                new HibernateJpaVendorAdapter()
        );

        return factory;

//        return builder
//                .dataSource(dataSource)
//                .packages("com.krishna.statecollect_batch.entity")
//                .persistenceUnit("second")
//                .build();
    }

    @Bean("secondTransactionManager")
    public PlatformTransactionManager secondTransactionManager(
            @Qualifier("secondEntityManagerFactory")
            EntityManagerFactory entityManagerFactory) {

        return new JpaTransactionManager(entityManagerFactory);
    }
}

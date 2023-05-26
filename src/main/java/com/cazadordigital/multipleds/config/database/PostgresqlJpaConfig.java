package com.cazadordigital.multipleds.config.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.cazadordigital.multipleds.persistence.postgresql.repository",
        entityManagerFactoryRef = "postgresqlEMF",
        transactionManagerRef = "postgresqlTrxManager"
)
@EnableTransactionManagement
public class PostgresqlJpaConfig {

    @Bean("postgresqlEMF")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            @Qualifier("postgresqlDatasource") DataSource postgresqlDS,
            EntityManagerFactoryBuilder builder){

        Map<String, String> additionalProps = new HashMap<>();
        additionalProps.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        return builder
                .dataSource(postgresqlDS)
                .persistenceUnit("postgresql")
                .properties(additionalProps)
                .packages("com.cazadordigital.multipleds.persistence.postgresql.entity")
                .build();

    }

    @Bean("postgresqlTrxManager")
    public JpaTransactionManager getMysqlTrxManager(@Qualifier("postgresqlEMF") LocalContainerEntityManagerFactoryBean mysqlEMF ){
        return new JpaTransactionManager(Objects.requireNonNull(mysqlEMF.getObject()));
    }

}

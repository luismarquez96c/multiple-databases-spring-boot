package com.cazadordigital.multipleds.config.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        basePackages = "com.cazadordigital.multipleds.persistence.mysql.repository",
        entityManagerFactoryRef = "mysqlEMF",
        transactionManagerRef = "mysqlTrxManager"
)
@EnableTransactionManagement
public class MysqlJpaConfig {

    @Bean
    public EntityManagerFactoryBuilder getEntityManagerFactoryBuilder(){
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }

    @Primary
    @Bean("mysqlEMF")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            @Qualifier("mysqlDatasource") DataSource mysqlDS,
            EntityManagerFactoryBuilder builder){

        Map<String, String> additionalProps = new HashMap<>();
        additionalProps.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

        return builder
                .dataSource(mysqlDS)
                .persistenceUnit("mysql")
                .properties(additionalProps)
                .packages("com.cazadordigital.multipleds.persistence.mysql.entity")
                .build();

    }

    @Primary
    @Bean("mysqlTrxManager")
    public JpaTransactionManager getMysqlTrxManager(@Qualifier("mysqlEMF") LocalContainerEntityManagerFactoryBean mysqlEMF ){
        return new JpaTransactionManager(Objects.requireNonNull(mysqlEMF.getObject()));
    }

}

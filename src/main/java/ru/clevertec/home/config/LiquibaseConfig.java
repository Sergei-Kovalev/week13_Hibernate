package ru.clevertec.home.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Value("${liquibase.enabled}")
    private boolean enabled;

    @Value("${liquibase.changelog}")
    private String changelog;

    @Value("${liquibase.drop}")
    private boolean drop;

    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changelog);
        liquibase.setShouldRun(enabled);
        liquibase.setDropFirst(drop);
        return liquibase;
    }
}

package br.edu.ifrn.agendamento.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "br.edu.ifrn.agendamento.audit.repository", // Criaremos esta pasta na Fase 3
    entityManagerFactoryRef = "auditoriaEntityManager",
    transactionManagerRef = "auditoriaTransactionManager"
)
public class AuditoriaDBConfig {

    @Bean(name = "auditoriaDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.auditoria")
    public DataSource auditoriaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "auditoriaEntityManager")
    public LocalContainerEntityManagerFactoryBean auditoriaEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("auditoriaDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("br.edu.ifrn.agendamento.audit.model") // Criaremos esta pasta na Fase 2
                .persistenceUnit("auditoria")
                .build();
    }

    @Bean(name = "auditoriaTransactionManager")
    public PlatformTransactionManager auditoriaTransactionManager(
            @Qualifier("auditoriaEntityManager") LocalContainerEntityManagerFactoryBean auditoriaEntityManager) {
        return new JpaTransactionManager(Objects.requireNonNull(auditoriaEntityManager.getObject()));
    }
}
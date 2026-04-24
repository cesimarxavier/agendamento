package br.edu.ifrn.agendamento.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = "br.edu.ifrn.agendamento.repository", // marcáo de onde ficarão os repositórios principais
    entityManagerFactoryRef = "principalEntityManager",
    transactionManagerRef = "principalTransactionManager"
)
public class PrincipalDBConfig {

    @Primary // marcação que este sejá o banco padrão
    @Bean(name = "principalDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.principal")
    public DataSource principalDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "principalEntityManager")
    public LocalContainerEntityManagerFactoryBean principalEntityManager(
            EntityManagerFactoryBuilder builder,
            @Qualifier("principalDataSource") DataSource dataSource) {
        return builder
                .dataSource(dataSource)
                .packages("br.edu.ifrn.agendamento.model") // Onde ficarão as entidades principais (Aluno, Agendamento)
                .persistenceUnit("principal")
                .build();
    }

    @Primary
    @Bean(name = "principalTransactionManager")
    public PlatformTransactionManager principalTransactionManager(
            @Qualifier("principalEntityManager") LocalContainerEntityManagerFactoryBean principalEntityManager) {
        return new JpaTransactionManager(Objects.requireNonNull(principalEntityManager.getObject()));
    }
}
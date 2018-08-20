package ru.newdv.config;

import org.hibernate.cfg.Environment;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.newdv.entity.source.SourceTable;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
                    entityManagerFactoryRef = "sourceEntityManagerFactory",
                    transactionManagerRef = "sourceTransactionManager",
                    basePackages = "ru.newdv.repo.source"
)
public class SourceDbConfig {
    @Bean
    @Primary
    PlatformTransactionManager sourceTransactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                sourceEntityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    @Primary
    LocalContainerEntityManagerFactoryBean sourceEntityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(false);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(sourceDataSource());
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPackagesToScan(SourceTable.class.getPackage().getName());
        factoryBean.setJpaProperties(new Properties() {{
            put(Environment.DEFAULT_SCHEMA, "public");
            put(Environment.FORMAT_SQL, "true");
            put(Environment.PHYSICAL_NAMING_STRATEGY, "ru.newdv.config.SourceConfigNamingStrategy");
        }});
        return factoryBean;
    }

    @Bean
    @Primary
    @ConfigurationProperties("source.datasource")
    public DataSourceProperties sourceDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("source.datasource")
    public DataSource sourceDataSource() {
        return sourceDataSourceProperties().initializeDataSourceBuilder().build();
    }
}

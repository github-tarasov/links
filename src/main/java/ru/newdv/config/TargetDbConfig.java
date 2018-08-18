package ru.newdv.config;

import org.hibernate.cfg.Environment;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.newdv.entity.targett.TargetTable;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "targetEntityManagerFactory",
        transactionManagerRef = "targetTransactionManager",
        basePackages = "ru.newdv.repo.targett"
)
public class TargetDbConfig {
    @Bean
    PlatformTransactionManager targetTransactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                targetEntityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean targetEntityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(false);
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(targetDataSource());
        factoryBean.setJpaVendorAdapter(vendorAdapter);
        factoryBean.setPackagesToScan(TargetTable.class.getPackage().getName());
        factoryBean.setMappingResources("TargetTable.hbm.xml");
        factoryBean.setJpaProperties(new Properties() {{
            put(Environment.DEFAULT_SCHEMA, "public");
            put(Environment.FORMAT_SQL, "true");
        }});
        return factoryBean;
    }

    @Bean
    @ConfigurationProperties("target.datasource")
    public DataSourceProperties targetDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("target.datasource")
    public DataSource targetDataSource() {
        return targetDataSourceProperties().initializeDataSourceBuilder().build();
    }
}


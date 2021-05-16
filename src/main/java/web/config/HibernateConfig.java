package web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "web")
@EnableJpaRepositories("web.repository")
@EnableTransactionManagement(proxyTargetClass = true)
@PropertySource(value = "classpath:db.properties")
public class HibernateConfig {
    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment){
        this.environment = environment;
    }

    private Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.dialect",environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

    @Bean
    public DataSource dataSource(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("db.driver"));
        dataSource.setUrl(environment.getRequiredProperty("db.url"));
        dataSource.setUsername(environment.getRequiredProperty("db.username"));
        dataSource.setPassword(environment.getRequiredProperty("db.password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean lcemf = new LocalContainerEntityManagerFactoryBean();
        lcemf.setDataSource(dataSource());
        lcemf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        lcemf.setPackagesToScan("web.model");
        lcemf.setJpaProperties(hibernateProperties());
        return lcemf;
    }

    @Bean
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return jpaTransactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor translationPostProcessor(){
        return new PersistenceExceptionTranslationPostProcessor();
    }




}

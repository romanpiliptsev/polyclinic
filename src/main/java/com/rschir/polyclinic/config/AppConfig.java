package com.rschir.polyclinic.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan("com.rschir.polyclinic")
@PropertySource({"classpath:database.properties"})
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource myDataSource() {
        // Create connection pool
        ComboPooledDataSource myDataSource = new ComboPooledDataSource();

        // Set the jdbc driver
        try {
            myDataSource.setDriverClass(env.getProperty("spring.datasource.driver-class-name"));
        } catch (PropertyVetoException exc) {
            throw new RuntimeException(exc);
        }

        // Set database connection props
        myDataSource.setJdbcUrl(env.getProperty("spring.datasource.url"));
        myDataSource.setUser(env.getProperty("spring.datasource.username"));
        myDataSource.setPassword(env.getProperty("spring.datasource.password"));

        // Set connection pool props
        myDataSource.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
        myDataSource.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
        myDataSource.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
        myDataSource.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
        return myDataSource;
    }

    private Properties getHibernateProperties() {
        // Set hibernate properties
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        return props;
    }

    // Read environment property and convert to int
    private int getIntProperty(String propName) {
        String propVal = env.getProperty(propName);
        int intPropVal = Integer.parseInt(propVal);
        return intPropVal;
    }

    @Bean(name="entityManagerFactory")
    public LocalSessionFactoryBean sessionFactory() {
        // Create session factory
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // Set the properties
        sessionFactory.setDataSource(myDataSource());
        sessionFactory.setPackagesToScan(env.getProperty("hibernate.packagesToScan"));
        sessionFactory.setHibernateProperties(getHibernateProperties());

        return sessionFactory;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        // setup transaction manager based on session factory
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);

        return txManager;
    }
}

package com.example.demo.configuration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.example.demo.dto.UserDTO;


@Configuration
public class DBConfig {
	 @Value("${spring.datasource.dbcp2.driver}")
	  private String DB_DRIVER;
	  
	  @Value("${spring.datasource.password}")
	  private String DB_PASSWORD;
	  
	  @Value("${spring.datasource.url}")
	  private String DB_URL;
	  
	  @Value("${spring.datasource.username}")
	  private String DB_USERNAME;

	  @Value("${spring.jpa.properties.hibernate.dialect}")
	  private String HIBERNATE_DIALECT;
	  
	  @Value("${hibernate.show_sql}")
	  private String HIBERNATE_SHOW_SQL;
	  
	  @Value("${spring.jpa.hibernate.ddl-auto}")
	  private String HIBERNATE_HBM2DDL_AUTO;

	  @Value("${entitymanager.packagesToScan}")
	  private String ENTITYMANAGER_PACKAGES_TO_SCAN;
	  
	  @Bean
	  public DataSource dataSource() {
	    DriverManagerDataSource dataSource = new DriverManagerDataSource();
	    dataSource.setDriverClassName(DB_DRIVER);
	    dataSource.setUrl(DB_URL);
	    dataSource.setUsername(DB_USERNAME);
	    dataSource.setPassword(DB_PASSWORD);
	    return dataSource;
	  }

	  @Bean
	  public LocalSessionFactoryBean sessionFactory() {
	    LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
	    sessionFactoryBean.setDataSource(dataSource());
//	    sessionFactoryBean.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
	    sessionFactoryBean.setAnnotatedClasses(UserDTO.class);

	    Properties hibernateProperties = new Properties();
	    hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
	    hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
	    hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
	    sessionFactoryBean.setHibernateProperties(hibernateProperties);
	    
	    return sessionFactoryBean;
	  }

	  @Bean
	  public HibernateTransactionManager transactionManager() {
	    HibernateTransactionManager transactionManager = 
	        new HibernateTransactionManager();
	    transactionManager.setSessionFactory(sessionFactory().getObject());
	    return transactionManager;
	  }
	  
//	@Autowired
//	private EntityManagerFactory entityManagerFactory;
//
//	@Bean
//	public SessionFactory getSessionFactory() {
//	    if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
//	        throw new NullPointerException("factory is not a hibernate factory");
//	    }
//	    return entityManagerFactory.unwrap(SessionFactory.class);
//	}
}
package com.stackroute.keepnote.config;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/*This class will contain the application-context for the application. 
 * Define the following annotations:
 * @Configuration - Annotating a class with the @Configuration indicates that the 
 *                  class can be used by the Spring IoC container as a source of 
 *                  bean definitions
 * @ComponentScan - this annotation is used to search for the Spring components amongst the application
 * @EnableWebMvc - Adding this annotation to an @Configuration class imports the Spring MVC 
 * 				   configuration from WebMvcConfigurationSupport 
 * @EnableTransactionManagement - Enables Spring's annotation-driven transaction management capability.
 *                  
 * 
 * */
@Configuration
@ComponentScan(basePackages="com.stackroute.keepnote")
@EnableWebMvc
@EnableTransactionManagement
@EnableAspectJAutoProxy
public class ApplicationContextConfig {

	/*
	 * Define the bean for DataSource. In our application, we are using MySQL as the
	 * dataSource. To create the DataSource bean, we need to know: 1. Driver class
	 * name 2. Database URL 3. UserName 4. Password
	 */
	@Bean
	DriverManagerDataSource getDataSource() {
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/" + System.getenv("MYSQL_DATABASE")
				+"?verifyServerCertificate=false&useSSL=false&requireSSL=false");
		dataSource.setUsername(System.getenv("MYSQL_USER"));
		dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));
	/*	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/keepnte");
		dataSource.setUsername("root");
		dataSource.setPassword("root");*/
	
		return dataSource;
	}
	/*
	 * Use this configuration while submitting solution in hobbes.
	 * dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	 * dataSource.setUrl("jdbc:mysql://" + System.getenv("MYSQL_HOST") + ":3306/" +
	 * System.getenv("MYSQL_DATABASE")
	 * +"?verifyServerCertificate=false&useSSL=false&requireSSL=false");
	 * dataSource.setUsername(System.getenv("MYSQL_USER"));
	 * dataSource.setPassword(System.getenv("MYSQL_PASSWORD"));
	 */

	/*
	 * create a getter for Hibernate properties here we have to mention 1. show_sql
	 * 2. Dialect 3. hbm2ddl
	 */
	
	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "update");
		
		return properties;
	}

	/*
	 * Define the bean for SessionFactory. Hibernate SessionFactory is the factory
	 * class through which we get sessions and perform database operations.
	 */
	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(getDataSource());
		sessionFactory.setPackagesToScan(new String[] {"com.stackroute.keepnote.model"});
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		return  sessionFactory;
		
	}
	/*
	 * Define the bean for Transaction Manager. HibernateTransactionManager handles
	 * transaction in Spring. The application that uses single hibernate session
	 * factory for database transaction has good choice to use
	 * HibernateTransactionManager. HibernateTransactionManager can work with plain
	 * JDBC too. HibernateTransactionManager allows bulk update and bulk insert and
	 * ensures data integrity.
	 */
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(sessionFactory);
		
		return transactionManager;
	}
	

	@Bean
	InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");
		
		return viewResolver;
	}

}

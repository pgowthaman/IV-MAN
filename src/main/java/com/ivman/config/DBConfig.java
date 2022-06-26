package com.ivman.config;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ivman.model.CompanyModel;
import com.ivman.model.UserModel;
import com.ivman.model.UserRoleModel;


/**
 * 
 * @author iamgo
 *
 */
@Configuration
@EnableTransactionManagement 
public class DBConfig {

	@Bean 
	public SessionFactory sessionFactory() throws URISyntaxException { 
		LocalSessionFactoryBuilder lsf= new LocalSessionFactoryBuilder(getDataSource()); 
		Properties hibernateProperties = new Properties();

		hibernateProperties.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQL81Dialect");
		//hibernateProperties.setProperty("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		hibernateProperties.setProperty("hibernate.show_sql", "false");
		hibernateProperties.setProperty("hibernate.format_sql", "true");
		hibernateProperties.setProperty("hibernate.use_sql_comments", "true");
		hibernateProperties.setProperty("hibernate.enable_lazy_load_no_trans", "true");

		lsf.addProperties(hibernateProperties); 
		lsf.addAnnotatedClass(UserModel.class)
		.addAnnotatedClass(CompanyModel.class)
		.addAnnotatedClass(UserRoleModel.class)
		; 
		return lsf.buildSessionFactory(); }

//	@Bean 
//	public DataSource getDataSource() throws URISyntaxException { 
//
//		URI dbUri = new URI("postgres://zmclzpqmfckfgh:bd320530841f56f0a058727098269511e5c4bbe1ccc11819e38076a137a4a0bb@ec2-52-86-25-51.compute-1.amazonaws.com:5432/d9atvrga2nbf1o");
//
//		String username = dbUri.getUserInfo().split(":")[0];
//		String password = dbUri.getUserInfo().split(":")[1];
//		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
//
//		BasicDataSource dataSource = new BasicDataSource(); dataSource.setDriverClassName("org.postgresql.Driver");
//		dataSource.setUrl(dbUrl);
//		dataSource.setUsername(username);
//		dataSource.setPassword(password); 
//		return dataSource; 
//	}
	
	@Bean 
	public DataSource getDataSource() throws URISyntaxException { 

		URI dbUri = new URI("postgresql://postgres:EDDL7eXwD0g5bUWW8zBg@containers-us-west-71.railway.app:5624/railway");

		String username = dbUri.getUserInfo().split(":")[0];
		String password = dbUri.getUserInfo().split(":")[1];
		String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";

		BasicDataSource dataSource = new BasicDataSource(); dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(dbUrl);
		dataSource.setUsername(username);
		dataSource.setPassword(password); 
		return dataSource; 
	}

	@Bean 
	public HibernateTransactionManager hibTransManager() throws URISyntaxException {
		HibernateTransactionManager htm = new HibernateTransactionManager();
	    htm.setTransactionSynchronization(HibernateTransactionManager.SYNCHRONIZATION_ALWAYS);
	    htm.setSessionFactory(sessionFactory());
		
		return htm; 
	} 
}

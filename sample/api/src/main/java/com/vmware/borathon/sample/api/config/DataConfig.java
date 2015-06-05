package com.vmware.borathon.sample.api.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.vmware.borathon.sample.api.repository")
@ComponentScan("com.vmware.borathon.sample.api.domain")
@PropertySource("classpath:db.properties")
public class DataConfig {

   private final static String ENV_VAR_NAME_DB_URL = "DB_DOCKER_URL";
   private final static String ENV_VAR_NAME_DB_PORT = "DB_DOCKER_PORT";

   private final static String PROPERTY_NAME_DB_URL = "db.url";
   private final static String PROPERTY_NAME_DB_PORT = "db.port";
   private final static String PROPERTY_NAME_DB_NAME = "db.name";
   private final static String PROPERTY_NAME_DB_USERNAME = "db.username";
   private final static String PROPERTY_NAME_DB_PASSWORD = "db.password";
   private final static String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
   private final static String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
   private final static String PROPERTY_NAME_HIBERNATE_EJB_NAMING_STRATEGY = "hibernate.ejb.naming_strategy";
   private final static String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
   private final static String PROPERTY_NAME_ENTITY_MANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";

   @Autowired
   private Environment environment;

   @Bean
   public DataSource dataSource() {
      PGSimpleDataSource dataSource = new PGSimpleDataSource();
      Map<String, String> env = System.getenv();
      String msg = "";
      for (Entry<String, String> v : env.entrySet()) {
         msg += (v.getKey() + "=" + v.getValue());
      }
      throw new RuntimeException(msg);
      // URL and PORT for database from environment variables take precedence over properties
      // String dbUrl = System.getenv(ENV_VAR_NAME_DB_URL);
      // String dbPort = System.getenv(ENV_VAR_NAME_DB_PORT);
      //
      // dataSource.setServerName(StringUtils.isEmpty(dbUrl)
      // ? environment.getProperty(PROPERTY_NAME_DB_URL)
      // : dbUrl);
      // dataSource.setPortNumber(StringUtils.isEmpty(dbPort)
      // ? Integer.valueOf(environment.getProperty(PROPERTY_NAME_DB_PORT))
      // : Integer.valueOf(dbPort));
      // dataSource.setDatabaseName(environment.getProperty(PROPERTY_NAME_DB_NAME));
      // dataSource.setUser(environment.getProperty(PROPERTY_NAME_DB_USERNAME));
      // dataSource.setPassword(environment.getProperty(PROPERTY_NAME_DB_PASSWORD));
      //
      // return dataSource;
   }

   @Bean
   public JpaTransactionManager transactionManager() {
      JpaTransactionManager transactionManager = new JpaTransactionManager();

      transactionManager.setEntityManagerFactory(entityManagerFactory());

      return transactionManager;
   }

   @Bean
   public EntityManagerFactory entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
      Properties jpaProterties = new Properties();
      HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

      jpaProterties.put(PROPERTY_NAME_HIBERNATE_DIALECT, environment.getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
      jpaProterties
            .put(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, environment.getProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
      jpaProterties.put(PROPERTY_NAME_HIBERNATE_EJB_NAMING_STRATEGY,
            environment.getProperty(PROPERTY_NAME_HIBERNATE_EJB_NAMING_STRATEGY));
      jpaProterties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, environment.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));

      vendorAdapter.setGenerateDdl(true);

      entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
      entityManagerFactory.setDataSource(dataSource());
      entityManagerFactory
            .setPackagesToScan(environment.getProperty(PROPERTY_NAME_ENTITY_MANAGER_PACKAGES_TO_SCAN));
      entityManagerFactory.setJpaProperties(jpaProterties);
      entityManagerFactory.afterPropertiesSet();

      return entityManagerFactory.getObject();
   }
}

package sa.com.stc.customerverification.web.config;

import java.util.HashMap;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    entityManagerFactoryRef = "crmEntityManagerFactory",
    transactionManagerRef = "crmTransactionManager",
    basePackages = {"sa.com.stc.customerverification.web.infra.persistence.crm"})
public class CrmDatabaseConfig {

  @Bean(name = "crmDataSource")
  @ConfigurationProperties(prefix = "spring.crm.datasource")
  public DataSource dataSource() {
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "crmEntityManagerFactory")
  public LocalContainerEntityManagerFactoryBean crmEntityManagerFactory(
      EntityManagerFactoryBuilder builder, @Qualifier("crmDataSource") DataSource dataSource) {
    HashMap<String, Object> properties = new HashMap<>();
    // properties.put("hibernate.hbm2ddl.auto", "update");
    properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
    return builder
        .dataSource(dataSource)
        .properties(properties)
        .packages("sa.com.stc.customerverification.web.model.crm")
        .persistenceUnit("crmcustomer")
        .build();
  }

  @Bean(name = "crmTransactionManager")
  public PlatformTransactionManager crmTransactionManager(
      @Qualifier("crmEntityManagerFactory") EntityManagerFactory crmEntityManagerFactory) {
    return new JpaTransactionManager(crmEntityManagerFactory);
  }
}

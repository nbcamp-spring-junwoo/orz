package com.junwoo.ott.global.config.DatabaseReplication;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Slf4j
@Configuration
public class RoutingDataSourceConfig {

  private final String ROUTING_DATA_SOURCE = "routingDataSource";
  private final String MASTER_DATA_SOURCE = "masterDataSource";
  private final String SLAVE_DATA_SOURCE = "slaveDataSource";
  private final String DATA_SOURCE = "dataSource";

  @Bean(ROUTING_DATA_SOURCE)
  public DataSource routingDataSource(
      @Qualifier(MASTER_DATA_SOURCE) final DataSource masterDataSource,
      @Qualifier(SLAVE_DATA_SOURCE) final DataSource slaveDataSource) {

    RoutingDataSource routingDataSource = new RoutingDataSource();

    Map<Object, Object> dataSourceMap = new HashMap<>();
    dataSourceMap.put(DataSourceType.MASTER, masterDataSource);
    dataSourceMap.put(DataSourceType.SLAVE, slaveDataSource);

    routingDataSource.setTargetDataSources(dataSourceMap);
    routingDataSource.setDefaultTargetDataSource(masterDataSource);

    return routingDataSource;
  }

  @Primary
  @Bean(DATA_SOURCE)
  public DataSource dataSource(
      @Qualifier(ROUTING_DATA_SOURCE) DataSource routingDataSource) {
    return new LazyConnectionDataSourceProxy(routingDataSource);
  }

//  @Bean("entityManagerFactory")
//  public LocalContainerEntityManagerFactoryBean entityManagerFactory(
//      @Qualifier(DATA_SOURCE) DataSource dataSource) {
//    LocalContainerEntityManagerFactoryBean entityManagerFactory
//        = new LocalContainerEntityManagerFactoryBean();
//    entityManagerFactory.setDataSource(dataSource);
//    entityManagerFactory.setPackagesToScan("com.junwoo.ott.*");
//    entityManagerFactory.setJpaVendorAdapter(this.jpaVendorAdapter());
//    entityManagerFactory.setPersistenceUnitName("entityManager");
//    return entityManagerFactory;
//  }
//
//  private JpaVendorAdapter jpaVendorAdapter() {
//    HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
//    hibernateJpaVendorAdapter.setGenerateDdl(true);
//    hibernateJpaVendorAdapter.setShowSql(true);
//    hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQLDialect");
//    return hibernateJpaVendorAdapter;
//  }
//
//  @Bean("transactionManager")
//  public PlatformTransactionManager platformTransactionManager(
//      @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory) {
//    JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
//    jpaTransactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
//    return jpaTransactionManager;
//  }

}

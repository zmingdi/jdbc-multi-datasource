/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.datasource;

import javax.annotation.PostConstruct;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
/**
 *
 * @author mzhang457
 */
@Service
public class DataSourceManager {
  
  private static final String DB_CONNECTION_URL_TEMPLATE="jdbc:sqlserver://[hostname]:1433;databaseName=[db_name]";
//  @Autowired
//  ApplicationContext ctx;
  @Autowired
  ConfigurableBeanFactory beanFactory;
  
  DataSource initDataSource(String hostName, String dbName) {

    DataSource ds = new DataSource();
    ds.setUsername("act_admin");
    ds.setPassword("Password1");
    ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    ds.setUrl(DB_CONNECTION_URL_TEMPLATE.replace("[hostname]", hostName).replace("[db_name]", dbName));
    ds.setTestOnBorrow(true);
    ds.setMinEvictableIdleTimeMillis(300000);
    ds.setValidationQuery("SELECT 1");
    ds.setLogAbandoned(true);
    ds.setMaxIdle(15);
    ds.setMaxActive(30);
    ds.setMinIdle(1);
    return ds;
  }
  
  @PostConstruct
  public void initTemplates() {
    DataSource clientDs = this.initDataSource("localhost", "ACT_CLIENT_PWC");
    JdbcTemplate clientTemplate = new JdbcTemplate(clientDs);
    beanFactory.registerSingleton("clientTemplate", clientTemplate);
    
    DataSource masterDs = this.initDataSource("localhost", "ACT_Master");
    JdbcTemplate masterTemplate = new JdbcTemplate(masterDs);
    beanFactory.registerSingleton("masterTemplate", masterTemplate);
  }
  
  public JdbcTemplate getTemplate(String templateName) throws Exception {
    if(beanFactory.containsBean(templateName)){
      return (JdbcTemplate)beanFactory.getBean(templateName);
    } else {
      throw new Exception("invalid templateName");
    }
  }
}

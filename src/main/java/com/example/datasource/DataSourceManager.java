/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.datasource;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
/**
 *
 * @author mzhang457
 */
//@EnableTransactionManagement
@Service
public class DataSourceManager {
  
  private static final String DB_CONNECTION_URL_TEMPLATE="jdbc:sqlserver://[hostname]:1433;databaseName=[db_name]";

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
    DataSourceTransactionManager dstmc = new DataSourceTransactionManager(clientDs);
    JdbcTemplate clientTemplate = new JdbcTemplate(clientDs);
    TransactionTemplate clientTxTemplate = new TransactionTemplate(dstmc);
    clientTxTemplate.setIsolationLevel(TransactionTemplate.ISOLATION_READ_UNCOMMITTED);
    beanFactory.registerSingleton("clientDataSource", clientDs);
    beanFactory.registerSingleton("clientTemplate", clientTemplate);
    beanFactory.registerSingleton("clientTemplateTxM", dstmc);
    beanFactory.registerSingleton("clientTxTemplate", clientTxTemplate);

    DataSource masterDs = this.initDataSource("localhost", "ACT_Master");
    JdbcTemplate masterTemplate = new JdbcTemplate(masterDs);
    DataSourceTransactionManager dstmm = new DataSourceTransactionManager(clientDs);
    TransactionTemplate masterTxTemplate = new TransactionTemplate(dstmm);
    masterTxTemplate.setIsolationLevel(TransactionTemplate.ISOLATION_READ_UNCOMMITTED);
    beanFactory.registerSingleton("masterDataSource", masterDs);
    beanFactory.registerSingleton("masterTemplate", masterTemplate);
    beanFactory.registerSingleton("masterTemplateTxM", dstmm);
    beanFactory.registerSingleton("masterTxTemplate", masterTxTemplate);
  }
  
  public JdbcTemplate getTemplate(String templateName) throws Exception {
    if(beanFactory.containsBean(templateName+"Template")){
      return (JdbcTemplate)beanFactory.getBean(templateName+"Template");
    } else {
      throw new Exception("invalid templateName");
    }
  }
  public DataSource getDataSource(String dsName) throws Exception{
    if(beanFactory.containsBean(dsName+"DataSource")){
      return (DataSource)beanFactory.getBean(dsName+"DataSource");
    } else {
      throw new Exception("invalid dsName");
    }
  }
  public JdbcTemplate getTemplate() throws Exception {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String templateName = (String)request.getAttribute("templateName");
    return getTemplate(templateName);
  }
  
  public TransactionTemplate getTxTemplate() throws Exception {
	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String templateName = (String)request.getAttribute("templateName");
    return getTxTemplate(templateName);
  }
  public TransactionTemplate getTxTemplate(String clientName) throws Exception {
	if (beanFactory.containsBean(clientName+"TxTemplate")) {
		return (TransactionTemplate) beanFactory.getBean(clientName+"TxTemplate");
	} else {
		throw new Exception("invalid dsName");
	}
  }
}

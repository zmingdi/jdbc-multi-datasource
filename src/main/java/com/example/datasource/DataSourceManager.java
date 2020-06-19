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
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.master.repository.ClientRepository;
import com.example.model.Client;
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
  
  @Autowired
  DataSourceProperties properties;
  
  @Autowired
  ClientRepository clientRepo;
  
  public static final String POSTFIX_DATASOURCE = "-DataSource";
  public static final String POSTFIX_JDBCTEMPLATE = "-Template";
  public static final String POSTFIX_TX_MANAGER = "-TxManager";
  public static final String POSTFIX_TX_TEMPLATE = "-TxTemplate";
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
  
  DataSource initDataSource(Client client) {
	    DataSource ds = new DataSource();
	    ds.setUsername(client.getClientDBUserName());
	    ds.setPassword(client.getClientDBPassword());
	    ds.setDriverClassName(properties.getDriverClassName());
	    ds.setUrl(DB_CONNECTION_URL_TEMPLATE.replace("[hostname]", client.getClientDBHost()).replace("[db_name]", client.getClientDBName()));
	    ds.setTestOnBorrow(true);
	    ds.setMinEvictableIdleTimeMillis(300000);
	    ds.setValidationQuery("SELECT 1");
	    ds.setLogAbandoned(true);
	    ds.setMaxIdle(15);
	    ds.setMaxActive(30);
	    ds.setMinIdle(1);
	    return ds;
	  }
  
  
  
  private Boolean initTemplates(Client client) {
	DataSource clientDs = this.initDataSource(client);
	DataSourceTransactionManager dstmc = new DataSourceTransactionManager(clientDs);
	JdbcTemplate clientTemplate = new JdbcTemplate(clientDs);
	TransactionTemplate clientTxTemplate = new TransactionTemplate(dstmc);
	clientTxTemplate.setIsolationLevel(TransactionTemplate.ISOLATION_READ_UNCOMMITTED);
	beanFactory.registerSingleton(client.getClientName()+POSTFIX_DATASOURCE, clientDs);
	beanFactory.registerSingleton(client.getClientName()+POSTFIX_JDBCTEMPLATE, clientTemplate);
	beanFactory.registerSingleton(client.getClientName()+POSTFIX_TX_MANAGER, dstmc);
	beanFactory.registerSingleton(client.getClientName()+POSTFIX_TX_TEMPLATE, clientTxTemplate);
	return true;
  }
  public JdbcTemplate getTemplate() throws Exception {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String templateName = (String)request.getHeader("clientName");
    return getTemplate(templateName);
  }
  
  private JdbcTemplate getTemplate(String clientName) throws Exception {
    if(!isClientActivated(clientName)) {
		throw new RuntimeException("fail to init template");
	}
    if(beanFactory.containsBean(clientName+POSTFIX_JDBCTEMPLATE)){
      return (JdbcTemplate)beanFactory.getBean(clientName+POSTFIX_JDBCTEMPLATE);
    } else {
      throw new Exception("invalid templateName");
    }
  }
  
  public DataSource getDataSource() {
    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String clientName = (String)request.getHeader("clientName");
    return getDataSource(clientName);
  }
  private DataSource getDataSource(String clientName){
    if(!isClientActivated(clientName)) {
		throw new RuntimeException("fail to init template");
	}
    if(beanFactory.containsBean(clientName+POSTFIX_DATASOURCE)){
      return (DataSource)beanFactory.getBean(clientName+POSTFIX_DATASOURCE);
    } else {
      throw new RuntimeException("invalid dsName");
    }
  }
  
  
  public TransactionTemplate getTxTemplate() throws Exception {
	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    HttpServletRequest request = attributes.getRequest();
    String templateName = (String)request.getHeader("clientName");
    return getTxTemplate(templateName);
  }
  private TransactionTemplate getTxTemplate(String clientName) throws Exception {
    if(!isClientActivated(clientName)) {
		throw new RuntimeException("fail to init tx template");
	}
	if (beanFactory.containsBean(clientName+POSTFIX_TX_TEMPLATE)) {
		return (TransactionTemplate) beanFactory.getBean(clientName+POSTFIX_TX_TEMPLATE);
	} else {
		throw new Exception("invalid clientName");
	}
  }
  
  public Boolean isClientActivated(String clientName) {
	  if(beanFactory.containsBean(clientName + POSTFIX_DATASOURCE)) {
		  return true;
	  } else {
		  Client client = clientRepo.findByClientName(clientName);
		  if(null == client) {
			  throw new RuntimeException("not client found, client name = " + clientName);
		  } else {
			  return initTemplates(client);
		  }
	  }
  }
}

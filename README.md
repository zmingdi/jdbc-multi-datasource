# Jdbc-multi-datasource
## Create multi-data-source support by JDBC Template
+ Initialize Datasource, JdbcTemplate, Transaction Manager for multiple datasource in DataSourceManager.  
  
+ Add getTemplate, getDataSource, getTransactionTemplate in DataSourceManager.    
  
+ Call DB operation in service level (not a good practise, just for POC).Using JDBCTemplate to make the operations by getTemplate() from DataSourceManager.  
  
+ Using AOP in TransactionMonitor.class to aspect @annotation("TestTransactional").  
   
    +  If @EnableTransactionManager, and use @Transactional, there will be issue with multiple txManager found, spring will not able to decide the txManager.
    
    + In AOP, if no pointcut without parameter in @TestTransactional defined, there will be issue during service startup, with "0 pointcut...." error.
      
 + Use http header "template" as datasource key.  
   
    + Define swagger config and apply seperate header param, then it can work in swagger UI. Can also work with multi-tenacy structure to seperate tenancy api call.  
    
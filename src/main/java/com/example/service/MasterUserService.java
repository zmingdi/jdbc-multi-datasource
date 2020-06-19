/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.service;

import com.example.model.UserClient;
import com.example.datasource.DataSourceManager;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 *
 * @author mzhang457
 */

@Service
public class MasterUserService {
  
  @Autowired
  DataSourceManager dsMgt;
  
  public Integer addUserOffice(UserClient user) {
    //TODO 
    return 0;
  }
  public List<String> listUserEmail(String templateName) throws Exception {
    
    return null;
  }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.master.repository;

import com.example.datasource.DataSourceManager;
import com.example.model.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author mzhang457
 */
public class ClientRepository {
  @Autowired
  DataSourceManager dsMgt;
  
  private static final String SQL_FIND_CLIENT_BY_ID = "select clientId, clientName from client where clientId=?";
  public Client findById(Integer id) throws Exception {
    JdbcTemplate template = dsMgt.getTemplate();
    return template.queryForObject(SQL_FIND_CLIENT_BY_ID, new Object[]{id}, Client.class);
  }
  
  private static final String SQL_INSER_CLIENT = "insert into client (clientid, clientName) values (?,?)";
  public Client insertClient(String clientId, String clientName) throws Exception{
    JdbcTemplate template = dsMgt.getTemplate();
    return null;
  }
}

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
import org.springframework.stereotype.Repository;

/**
 *
 * @author mzhang457
 */
@Repository
public class ClientRepository {
  @Autowired
  DataSourceManager dsMgt;
  
  @Autowired
  JdbcTemplate jdbcTemplate;
  
  private static final String SQL_FIND_CLIENT_BY_ID 
  	= "select clientName, clientDBName, clientDBHost, clientDBPort"
  			+ ", clientDBUsername, clientDBPassword  from client where clientName=?";
  public Client findByClientName(String clientName)  {
    return jdbcTemplate.queryForObject(SQL_FIND_CLIENT_BY_ID, new Object[]{clientName}
    	, (rs, rowNumber)->{
    	Client client = new Client();
    	client.setClientDBHost(rs.getString("clientDBHost"));
    	client.setClientDBPort(rs.getString("clientDBPort"));
    	client.setClientName(rs.getString("clientName"));
    	client.setClientDBName(rs.getString("clientDBName"));
    	client.setClientDBUserName(rs.getString("clientDBUsername"));
    	client.setClientDBPassword(rs.getString("clientDBPassword"));
    	return client;
    });
  }

}

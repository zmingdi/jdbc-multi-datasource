/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.model;

/**
 *
 * @author mzhang457
 */
public class Client {
  
  private Integer clientId;
  private String clientName;
  private String clientDBHost;
  private String clientDBPort;
  private String clientDBName;
  private String clientDBUserName;
  private String clientDBPassword;

  public Integer getClientId() {
    return clientId;
  }

  public void setClientId(Integer clientId) {
    this.clientId = clientId;
  }

  public String getClientName() {
    return clientName;
  }

  public void setClientName(String clientName) {
    this.clientName = clientName;
  }

public String getClientDBHost() {
	return clientDBHost;
}

public void setClientDBHost(String clientDBHost) {
	this.clientDBHost = clientDBHost;
}

public String getClientDBPort() {
	return clientDBPort;
}

public void setClientDBPort(String clientDBPort) {
	this.clientDBPort = clientDBPort;
}

public String getClientDBName() {
	return clientDBName;
}

public void setClientDBName(String clientDBName) {
	this.clientDBName = clientDBName;
}

public String getClientDBUserName() {
	return clientDBUserName;
}

public void setClientDBUserName(String clientDBUserName) {
	this.clientDBUserName = clientDBUserName;
}

public String getClientDBPassword() {
	return clientDBPassword;
}

public void setClientDBPassword(String clientDBPassword) {
	this.clientDBPassword = clientDBPassword;
}
  
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.service;

import com.example.datasource.DataSourceManager;
import com.example.monitor.TestTransactional;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 *
 * @author mzhang457
 */
@Service
public class OfficeService {

  @Autowired
  DataSourceManager dsMgt;
  
  public List<String> listOfficeNames(String templateName) throws Exception {
    JdbcTemplate clientTemplate = dsMgt.getTemplate(templateName);
    List<String> officeNames = Lists.newArrayList();
    clientTemplate.query("select officeName from [office]", rse->{
      officeNames.add(rse.getString(1));
    });
    return officeNames;
  }
  
  private static final String SQL_INSERT_OFFICE = "insert into Office "
       +" (officename, OfficeStreetAddress,OfficeLat,officelon, CompanyID) "
        +" values "
        +" (?,?, ?, ?,1) ";
  
  @TestTransactional
  public Integer insertOffice(String templateName) throws Exception {
    
    JdbcTemplate clientTemplate = dsMgt.getTemplate();
    Integer k = clientTemplate.update(SQL_INSERT_OFFICE
			, UUID.randomUUID().toString()
            ,UUID.randomUUID().toString(),"1.11","1.11");
	return k;
  }
}

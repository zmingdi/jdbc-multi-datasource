/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.service;

import com.example.datasource.DataSourceManager;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
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
}

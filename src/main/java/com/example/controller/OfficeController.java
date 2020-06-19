/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.service.OfficeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mzhang457
 */
@RestController
@RequestMapping("/office")
public class OfficeController {
  
  @Autowired
  OfficeService service;
  
  @GetMapping("/")
  public List<String> listOfficeNames() throws Exception {
    return service.listOfficeNames();
  }
  
  @PostMapping("/insertOfficeWithTemplate")
  public Integer insertOfficeWithTemplate(@RequestHeader String clientName) throws Exception {
    return service.insertOffice(clientName);
  }
  @PostMapping("/insertOfficeWithJdbcConn")
  public Boolean insertOfficeWithJdbcConn(@RequestHeader String clientName) throws Exception {
    return service.insertOfficeWithoutJdbcTemplate(clientName);
  }
}

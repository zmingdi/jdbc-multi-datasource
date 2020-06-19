/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.controller;

import com.example.service.MasterUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mzhang457
 */
//@RestController
//@RequestMapping("/masterUser")
public class MasterUsersController {
  
  @Autowired
  MasterUserService service;
  
  @GetMapping("/")
  public List<String> listEmails() throws Exception {
    return service.listUserEmail("masterTemplate");
  }
}

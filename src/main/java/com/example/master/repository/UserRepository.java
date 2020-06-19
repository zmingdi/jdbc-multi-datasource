/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.master.repository;

import com.example.datasource.DataSourceManager;
import com.example.model.UserClient;
import java.sql.Statement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author mzhang457
 */
public class UserRepository {
  @Autowired
  DataSourceManager dsMgt;
  private static final String SQL_INSERT_USER = "insert into [user] (ClientID, UserTypeID, UserEmail, ModifiedDatetime, ModifiedBy, CreatedBy,CreatedDatetime)"
          + "values (?,1,?, SYSDATETIMEOFFSET(),1,1, SYSDATETIMEOFFSET())"; 
  
  public UserClient insertUser(UserClient uc) throws Exception{
    JdbcTemplate template = dsMgt.getTemplate();
    KeyHolder holder = new GeneratedKeyHolder();
    template.update(conn->{
      return conn.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS);
    }, holder);
    uc.setUserId(null!=holder.getKey()?holder.getKey().intValue():0);
    return uc;
//            .update(SQL_INSERT_USER, uc.getClientId(), uc.getUserEmail());
  }
  private static final String SQL_FIND_USER_BY_EMAIL = "select userid, clientid, userEmail from [user] where [userEmail]=?";
  public UserClient findUserByEmail(String email) throws Exception {
    JdbcTemplate template = dsMgt.getTemplate();
    UserClient uc = template.queryForObject(SQL_FIND_USER_BY_EMAIL,new Object[]{email}, UserClient.class);
    return uc;
  }
}

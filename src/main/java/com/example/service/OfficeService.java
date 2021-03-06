/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.service;

import com.example.datasource.DataSourceManager;
import com.example.monitor.TestTransactional;
import com.google.common.collect.Lists;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.jdbc.pool.DataSource;
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

	public List<String> listOfficeNames() throws Exception {
		JdbcTemplate clientTemplate = dsMgt.getTemplate();
		List<String> officeNames = Lists.newArrayList();
		clientTemplate.query("select officeName from [office]", rse -> {
			officeNames.add(rse.getString(1));
		});
		return officeNames;
	}

	private static final String SQL_INSERT_OFFICE = "insert into Office "
			+ " (officename, OfficeStreetAddress,OfficeLat,officelon) " + " values " + " (?,?, ?, ?) ";

	/**
	 * Using combination of JdbcTemplate, and aspect by transaction template defined
	 * in AOP. the transaction shall be working.
	 */
	@TestTransactional
	public Integer insertOffice(String templateName) throws Exception {

		JdbcTemplate clientTemplate = dsMgt.getTemplate();
		Integer k = clientTemplate.update(SQL_INSERT_OFFICE, UUID.randomUUID().toString(), UUID.randomUUID().toString(),
				"1.11", "1.11");
		return k;
	}

	/**
	 * This will not working since we are using connections obtained from datasource
	 * directly. And it is bypassing the spring container management.
	 * 
	 * @param templateName
	 * @return
	 * @throws Exception
	 */
	@TestTransactional
	public Boolean insertOfficeWithoutJdbcTemplate(String templateName) throws Exception {
		DataSource ds = dsMgt.getDataSource();
		Connection conn = ds.getConnection();
		try (PreparedStatement prep = conn.prepareStatement(SQL_INSERT_OFFICE)) {
			prep.setString(1, UUID.randomUUID().toString());
			prep.setString(2, UUID.randomUUID().toString());
			prep.setString(3, "1.11");
			prep.setString(4, "1.11");
			return prep.execute();
		}
	}
}

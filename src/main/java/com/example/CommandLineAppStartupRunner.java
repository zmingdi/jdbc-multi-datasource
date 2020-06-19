/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.example.service.OfficeService;

/**
 *
 * @author mzhang457
 */
//@SpringBootApplication
//@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
	@Autowired
	private OfficeService myService;

	@Override
	public void run(String... args) throws Exception {
		myService.insertOffice("clientTemplate");
	}
	public static void main(String[] args) {
		SpringApplication.run(CommandLineAppStartupRunner.class, args);
	}
}

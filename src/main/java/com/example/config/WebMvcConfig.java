/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 *
 * @author mzhang457
 */
@Component
public class WebMvcConfig extends WebMvcConfigurerAdapter {
   @Autowired
   TemplateInteceptor productServiceInterceptor;

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(productServiceInterceptor);
   }
   @Override
   public void addViewControllers(ViewControllerRegistry registry) {
       registry.addRedirectViewController("/docApi/v2/api-docs", "/v2/api-docs");
       registry.addRedirectViewController("/docApi/swagger-resources/configuration/ui", "/swagger-resources/configuration/ui");
       registry.addRedirectViewController("/docApi/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
       registry.addRedirectViewController("/docApi/swagger-resources", "/swagger-resources");
   }

   @Override
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/docApi/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
       registry.addResourceHandler("/docApi/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
   }
}

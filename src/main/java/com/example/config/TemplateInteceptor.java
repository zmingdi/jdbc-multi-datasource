/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 *
 * @author mzhang457
 */
@Component
public class TemplateInteceptor implements HandlerInterceptor {
   @Override
   public boolean preHandle(
      HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      request.setAttribute("templateName", request.getHeader("templateName"));
      return true;
   }
//   @Override
//   public void postHandle(
//      HttpServletRequest request, HttpServletResponse response, Object handler, 
//      ModelAndView modelAndView) throws Exception {}
//   
//   @Override
//   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
//      Object handler, Exception exception) throws Exception {}
//    }
}

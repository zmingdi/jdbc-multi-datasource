/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.config;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.AbstractPathProvider;
import springfox.documentation.spring.web.plugins.Docket;
/**
 *
 * @author mzhang457
 */
@Configuration
public class SwaggerConfig {

  @Bean
  public Docket api() {
    // To set the header seperately, use following commented codes.
    List<Parameter> pars = Lists.newArrayList();
    ParameterBuilder tokenPar = new ParameterBuilder();
    tokenPar.name("templateName").description("templateName").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    pars.add(tokenPar.build());
    return new Docket(DocumentationType.SWAGGER_2).ignoredParameterTypes(HttpSession.class)
            .pathProvider(new BasePathProvider("/"))
            .select()
            //.apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.any())
            .build()
            .globalOperationParameters(pars)
            .securitySchemes(securitySchemes())
            .securityContexts(securityContexts());

  }

  @SuppressWarnings("rawtypes")

  private List<ApiKey> securitySchemes() {
    return Lists.newArrayList(new ApiKey("Authorization", "X-Auth-Token", "header"));
  }

  private List<SecurityContext> securityContexts() {
    return Lists.newArrayList(
            SecurityContext.builder()
                    .securityReferences(defaultAuth())
                    .forPaths(PathSelectors.any())
                    .build()
    );
  }

  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    List<SecurityReference> securityReferences = new ArrayList<>();
    securityReferences.add(new SecurityReference("Authorization", authorizationScopes));
    return securityReferences;
  }

  class BasePathProvider extends AbstractPathProvider {

    private String basePath;

    public BasePathProvider(String basePath) {
      this.basePath = basePath;
    }

    @Override
    protected String applicationPath() {
      return basePath;
    }

    @Override
    protected String getDocumentationPath() {
      return "/"; // keep it, don't need to change it for <domain/host>/contact-tracing/v2/api-docs
    }
  }
}

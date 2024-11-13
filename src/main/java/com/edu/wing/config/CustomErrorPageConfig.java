package com.edu.wing.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomErrorPageConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

  @Override
  public void customize(ConfigurableWebServerFactory factory) {
    factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
    factory.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
    factory.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/error/403"));
  }
}

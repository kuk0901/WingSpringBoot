package com.edu.dp.wingspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class WingSpringBootApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(WingSpringBootApplication.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(WingSpringBootApplication.class, args);
    System.out.println("!@#$!@#@#!#check");
  }

}

package com.edu.wing.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  @Value("${external.image.path:C:/upload/}")
  private String externalImagePath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // CSS 파일 경로 설정
    registry.addResourceHandler("/css/**")
        .addResourceLocations("classpath:/static/css/")
        .setCachePeriod(60 * 60 * 24 * 365);

    // JavaScript 파일 경로 설정
    registry.addResourceHandler("/js/**")
        .addResourceLocations("classpath:/static/js/")
        .setCachePeriod(60 * 60 * 24 * 365);

    // 이미지 파일 경로 설정(정적 리소스)
    registry.addResourceHandler("/img/**")
        .addResourceLocations("classpath:/static/img/")
        .setCachePeriod(60 * 60 * 24 * 365);

    // FIXME: 변경 필요
    // 외부 경로 이미지 설정
    registry.addResourceHandler("/external-images/**")
        .addResourceLocations("file:" + externalImagePath)
        .setCachePeriod(3600)
        .resourceChain(true)
        .addResolver(new PathResourceResolver());
  }
}

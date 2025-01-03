package com.edu.wing.config;

import com.edu.wing.auth.interceptor.AuthInterceptor;
import com.edu.wing.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
  @Autowired
  private AuthInterceptor authInterceptor;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {

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


    String externalImagePath = FileUtils.getFilePath(); // FileUtils에서 경로 가져오기
    registry.addResourceHandler("/img/card/**")
        .addResourceLocations("file:" + externalImagePath + "/") // 경로 끝에 / 추가
        .setCachePeriod(3600)
        .resourceChain(true)
        .addResolver(new PathResourceResolver());
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(authInterceptor)
        .addPathPatterns("/member/**", "/admin/**")
        .excludePathPatterns("/", "/auth/**", "/api/auth/**", "/css/**", "/js/**", "/images/**");
  }

}

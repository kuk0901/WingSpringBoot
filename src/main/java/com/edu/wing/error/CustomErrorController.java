package com.edu.wing.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

  private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

  @RequestMapping("/error/404") // 404 에러 처리
  public String handle404() {
    logger.warn("404 에러 발생");
    return "jsp/error/404"; // 404 JSP 페이지로 이동
  }

  @RequestMapping("/error/500") // 500 에러 처리
  public String handle500() {
    logger.warn("500 에러 발생");
    return "jsp/error/500"; // 500 JSP 페이지로 이동
  }

}
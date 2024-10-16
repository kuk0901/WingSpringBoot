package com.edu.wing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/")
  public String signin() {
    return "jsp/auth/SigninView";
  }
}

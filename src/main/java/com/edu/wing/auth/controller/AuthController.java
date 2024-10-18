package com.edu.wing.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
public class AuthController {

  @GetMapping("/signup")
  public String signup() { return "jsp/auth/SignupView"; }
}

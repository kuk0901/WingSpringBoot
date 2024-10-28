package com.edu.wing.auth.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("auth")
public class AuthController {

  @GetMapping("/signup")
  public String signup() { return "jsp/auth/SignupView"; }

  @GetMapping("/signout")
  public String signout(HttpSession session, RedirectAttributes redirectAttributes) {
    session.invalidate();
    redirectAttributes.addFlashAttribute("alertMsg", "로그아웃되었습니다.");
    return "redirect:/";
  }
}

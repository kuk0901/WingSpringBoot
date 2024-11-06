package com.edu.wing.auth.controller;

import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.util.RandomAlertMessage;
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
    MemberVo member = (MemberVo) session.getAttribute("member");
    RandomAlertMessage randomAlertMessage = new RandomAlertMessage();

    session.invalidate();

    if (member != null) {
      if ("MEMBER".equals(member.getGrade())) {
        redirectAttributes.addFlashAttribute("alertMsg", randomAlertMessage.getRandomMemberLogoutAlert());
      } else if ("ADMIN".equals(member.getGrade())) {
        redirectAttributes.addFlashAttribute("alertMsg", randomAlertMessage.getRandomAdminLogoutAlert());
      }
    }

    return "redirect:/";
  }

  @GetMapping("/find/account")
  public String findAccount() { return "jsp/auth/FindAccountView"; }

  @GetMapping("/find/password")
  public String findPassword() { return "jsp/auth/FindPasswordView"; }
}

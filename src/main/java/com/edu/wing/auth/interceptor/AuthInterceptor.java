package com.edu.wing.auth.interceptor;

import com.edu.wing.member.domain.MemberVo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class AuthInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    HttpSession session = request.getSession();
    String requestURI = request.getRequestURI();

    MemberVo member = (MemberVo) session.getAttribute("member");

    // 회원 전용 페이지 체크
    if (requestURI.startsWith("/member")) {
      if (member == null) {
        String message = "로그인이 필요한 서비스입니다.";
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        response.sendRedirect("/?message=" + encodedMessage);
        return false;
      }
    }

    // 관리자 전용 페이지 체크
    if (requestURI.startsWith("/admin")) {
      if (member == null || !"ADMIN".equals(member.getGrade())) {
        response.sendRedirect("/error/403");
        return false;
      }
    }

    return true;
  }
}

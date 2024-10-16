package com.edu.wing.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class AuthApiController {
  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestParam Map<String, String> userInfo) {
   // 결과를 담을 map
    Map<String, String> result = new HashMap<>();

    // 필수 필드 검증(이메일 중복 체크), 이메일 존재할 경우 message와 함께 return -> if문 사용


    try {
      // DB에 insert
      System.out.println("userInfo: " + userInfo);

      result.put("status", "success");
      result.put("message", "회원가입에 성공했습니다.");

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      result.put("status", "failed");
      result.put("message", "서버 오류로 인해 회원가입이 처리되지 못했습니다. 다시 한 번 시도해주세요.");
      return ResponseEntity.internalServerError().body(result);
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestParam Map<String, String> userInfo) {
    Map<String, String> result = new HashMap<>();

    try {
      result.put("status", "success");
      result.put("message", "로그인에 성공했습니다.");

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      result.put("status", "failed");
      result.put("message", "서버 오류로 인해 로그인이 처리되지 못했습니다. 다시 한 번 시도해주세요.");
      return ResponseEntity.internalServerError().body(result);
    }
  }
}

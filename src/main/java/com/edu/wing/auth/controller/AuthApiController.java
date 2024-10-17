package com.edu.wing.auth.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.edu.wing.auth.domain.AuthVo;
import com.edu.wing.auth.service.AuthService;

@RestController
@RequestMapping("api/auth")
public class AuthApiController {
  private Logger log = LoggerFactory.getLogger(AuthApiController.class);
  private final String logTitleMsg = "==AuthApiController==";

  @Autowired
  private AuthService authService;


  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody AuthVo authVo) {
    // 결과를 담을 map
    Map<String, String> result = new HashMap<>();

    try {

      authVo.setGrade("회원");  // 회원 등급 설정

      // DB에 insert
      authService.memberInsertOne(authVo);

      result.put("status", "success");
      result.put("message", "회원가입에 성공했습니다.");

      return ResponseEntity.ok().body(result);
    } catch (Exception e) {
      log.error("회원가입 중 오류 발생: ", e);
      result.put("status", "failed");
      result.put("message", "서버 오류로 인해 회원가입이 처리되지 못했습니다. 다시 시도해주세요.");
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

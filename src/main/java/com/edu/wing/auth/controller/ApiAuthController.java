package com.edu.wing.auth.controller;

import com.edu.wing.member.domain.MemberVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class ApiAuthController {

  private final Logger log = LoggerFactory.getLogger(ApiAuthController.class);
  private final String logTitleMsg = "==ApiAuthController==";

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody MemberVo memberVo) {
    log.info(logTitleMsg);
    log.info("memberVo: {}", memberVo);

    Map<String, String> resultMap = new HashMap<>();

    try {
      resultMap.put("status", "success");
      resultMap.put("msg", "회원가입에 성공했습니다.");

      return ResponseEntity.ok().body(resultMap);
    } catch (Exception e) {
      e.printStackTrace();
      resultMap.put("status", "failed");
      resultMap.put("msg", "서버 오류로 인해 회원가입이 처리되지 못했습니다. 다시 시도해 주세요.");

      return ResponseEntity.internalServerError().body(resultMap);
    }
  }
}

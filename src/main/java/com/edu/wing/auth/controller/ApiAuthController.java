package com.edu.wing.auth.controller;

import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @Autowired
  MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody MemberVo memberVo) {
    log.info(logTitleMsg);
    log.info("signup memberVo: {}", memberVo);

    Map<String, String> resultMap = new HashMap<>();

    try {

      if (memberService.isEmailAlreadyRegistered(memberVo.getEmail())) {
        resultMap.put("status", "failed");
        resultMap.put("email", memberVo.getEmail());
        resultMap.put("emailMsg", "이미 존재하는 이메일입니다.");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(resultMap);
      }

      memberVo.setGrade("MEMBER");
      if (memberService.memberInsertOne(memberVo)) {
        resultMap.put("status", "failed");
        resultMap.put("msg", "회원가입에 실패했습니다. 잠시 후 다시 시도해 주세요.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
      }

      resultMap.put("status", "success");
      resultMap.put("msg", "회원가입에 성공했습니다.");

      return ResponseEntity.ok().body(resultMap);
    } catch (Exception e) {
      log.error("회원가입 중 오류 발생: ", e);
      resultMap.put("status", "error");
      resultMap.put("msg", "서버 오류로 인해 회원가입이 처리되지 못했습니다. 다시 시도해 주세요.");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody MemberVo memberVo, HttpSession session) {
    log.info(logTitleMsg);
    log.info("signin memberVo: {}", memberVo);

    Map<String, String> resultMap = new HashMap<>();

    try {
      MemberVo user = memberService.memberExist(memberVo.getEmail(), memberVo.getPwd());

      if (user != null) {
        // 세션에 사용자 정보 저장
        session.setAttribute("member", user);

        /* FIXME: 관리자, 사용자에 따라 다른 문구 추가
         * 관리자 -> 문구 1개
         * 사용자 -> 문구 5개 중에서 랜덤
        */
        resultMap.put("status", "success");
        resultMap.put("msg", "로그인에 성공했습니다.");
        resultMap.put("grade", user.getGrade());

        return ResponseEntity.ok().body(resultMap);
      } else {
        resultMap.put("status", "fail");
        resultMap.put("msg", "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resultMap);
      }
    } catch (Exception e) {
      log.error(logTitleMsg, e);
      resultMap.put("status", "error");
      resultMap.put("msg", "서버 오류가 발생했습니다.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
  }
}

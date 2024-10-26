package com.edu.wing.auth.controller;

import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.member.service.MemberService;
import com.edu.wing.util.RandomAlertMessage;
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

  private final String STATUS = "status";
  private final String STATUS_SUCCESS = "success";
  private final String STATUS_FAIL = "failed";
  private final String STATUS_ERROR = "error";
  private final String ALERT_MSG = "alertMsg";

  private final RandomAlertMessage randomAlertMessage = new RandomAlertMessage();

  @Autowired
  MemberService memberService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody MemberVo memberVo) {
    log.info("signup memberVo: {}", memberVo);

    Map<String, String> resultMap = new HashMap<>();

    try {

      if (memberService.isEmailAlreadyRegistered(memberVo.getEmail())) {
        resultMap.put(STATUS, STATUS_FAIL);
        resultMap.put("email", memberVo.getEmail());
        resultMap.put("emailMsg", "이미 존재하는 이메일입니다.");

        return ResponseEntity.status(HttpStatus.CONFLICT).body(resultMap);
      }

      memberVo.setGrade("MEMBER");

      if (memberService.memberInsertOne(memberVo)) {
        resultMap.put(STATUS, STATUS_SUCCESS);
        resultMap.put(ALERT_MSG, "회원가입이 완료되었습니다.");
        return ResponseEntity.ok().body(resultMap);
      }

      resultMap.put(STATUS, STATUS_FAIL);
      resultMap.put(ALERT_MSG, "회원가입에 실패했습니다. 잠시 후 다시 시도해 주세요.");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
    } catch (Exception e) {
      log.error("회원가입 중 오류 발생: ", e);
      resultMap.put(STATUS, STATUS_ERROR);
      resultMap.put(ALERT_MSG, "서버 오류로 인해 회원가입이 처리되지 못했습니다. 다시 시도해 주세요.");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody MemberVo memberVo, HttpSession session) {
    log.info("signin memberVo: {}", memberVo);

    Map<String, String> resultMap = new HashMap<>();

    try {
      MemberVo user = memberService.memberExist(memberVo.getEmail(), memberVo.getPwd());

      if (user != null) {
        // 세션에 사용자 정보 저장
        session.setAttribute("member", user);

        resultMap.put(STATUS, STATUS_SUCCESS);
        resultMap.put(ALERT_MSG, user.getGrade().equals("ADMIN")
            ? randomAlertMessage.getRandomAdminAlert()
            : randomAlertMessage.getRandomMemberAlert());
        resultMap.put("grade", user.getGrade());

        return ResponseEntity.ok().body(resultMap);
      } else {
        resultMap.put(STATUS, STATUS_FAIL);
        resultMap.put(ALERT_MSG, "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(resultMap);
      }
    } catch (Exception e) {
      resultMap.put(STATUS, STATUS_ERROR);
      resultMap.put(ALERT_MSG, "서버 오류가 발생했습니다.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
  }
}

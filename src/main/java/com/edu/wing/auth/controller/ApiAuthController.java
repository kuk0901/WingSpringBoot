package com.edu.wing.auth.controller;

import com.edu.wing.cardBenefit.domain.CardBenefitVo;
import com.edu.wing.cardBenefit.service.CardBenefitService;
import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.member.service.MemberService;
import com.edu.wing.util.RandomAlertMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class ApiAuthController {
  private final String STATUS = "status";
  private final String STATUS_SUCCESS = "success";
  private final String STATUS_FAIL = "failed";
  private final String STATUS_ERROR = "error";
  private final String ALERT_MSG = "alertMsg";

  private final RandomAlertMessage randomAlertMessage = new RandomAlertMessage();

  @Autowired
  MemberService memberService;

  @Autowired
  CardBenefitService cardBenefitService;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody MemberVo memberVo) {
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
      resultMap.put(STATUS, STATUS_ERROR);
      resultMap.put(ALERT_MSG, "서버 오류로 인해 회원가입이 처리되지 못했습니다. 다시 시도해 주세요.");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
    }
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody MemberVo memberVo, HttpSession session) {
    Map<String, String> resultMap = new HashMap<>();

    try {
      MemberVo user = memberService.memberExist(memberVo.getEmail(), memberVo.getPwd());

      if (user != null) {
        // 세션에 사용자 정보 저장
        session.setAttribute("member", user);

        List<CardBenefitVo> cardBenefitVoList = cardBenefitService.userAccountBookDiscountRateList(user.getMemberNo());

        if (!cardBenefitVoList.isEmpty()) {
          for (CardBenefitVo benefit : cardBenefitVoList) {
            String key = "cardBenefit_" + benefit.getCardBenefitDivision();
            session.setAttribute(key, benefit.getCardPercentage());
          }
        }

        resultMap.put(STATUS, STATUS_SUCCESS);
        resultMap.put(ALERT_MSG, user.getGrade().equals("ADMIN")
            ? randomAlertMessage.getRandomAdminLoginAlert()
            : randomAlertMessage.getRandomMemberLoginAlert());
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

  @PostMapping("/find/account")
  public ResponseEntity<?> findMemberAccount(@RequestBody Map<String, String> map) {
    HashMap<String, Object> resultMap = new HashMap<>();

    MemberVo member = memberService.findMemberAccount(map);

    if (member == null) {
      resultMap.put(STATUS, STATUS_FAIL);
      resultMap.put("errorMsg", "존재하지 않는 회원입니다.");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
    }

    resultMap.put(STATUS, STATUS_SUCCESS);
    resultMap.put("member", member);

    return ResponseEntity.ok().body(resultMap);
  }

  @PostMapping("/find/password")
  public ResponseEntity<?> findMemberPassword(@RequestBody Map<String, String> map) {
    HashMap<String, Object> resultMap = new HashMap<>();

    MemberVo member = memberService.findMemberPassword(map);

    if (member == null) {
      resultMap.put(STATUS, STATUS_FAIL);
      resultMap.put("errorMsg", "존재하지 않는 회원입니다.");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
    }

    resultMap.put(STATUS, STATUS_SUCCESS);
    resultMap.put("member", member);

    return ResponseEntity.ok().body(resultMap);
  }

  // FIXME: 비밀번호 변경 로직 추가, Mapper 작성 완료, dao-service 추가
  @PatchMapping("/find/password")
  public ResponseEntity<?> updateMemberPassword(@RequestBody Map<String, String> map) {
    HashMap<String, Object> resultMap = new HashMap<>();

    map.put("memberNo", String.valueOf(memberService.findMemberPassword(map).getMemberNo()));

    boolean isPasswordChanged = memberService.changeMemberPasswordAndValidate(map);

    if (!isPasswordChanged) {
      resultMap.put(STATUS, STATUS_FAIL);
      resultMap.put(ALERT_MSG, "비밀번호 변경에 실패했습니다.");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
    }

    resultMap.put(STATUS, STATUS_SUCCESS);
    resultMap.put(ALERT_MSG, "비밀번호가 성공적으로 변경되었습니다. 변경된 비밀번호로 로그인해 주세요.");

    return ResponseEntity.ok().body(resultMap);
  }
}

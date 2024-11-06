package com.edu.wing.member.controller;

import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member/api/user")
public class MemberUserApiController {
    private Logger log = LoggerFactory.getLogger(MemberUserApiController.class);
    private final String logTitleMsg = "==MemberUserApiController==";

    @Autowired
    private MemberService memberService;
    private final String ALERT_MSG = "alertMsg";

    // 회원 정보 조회 API
    @GetMapping("/myPage/info")
    public MemberVo getMyPageInfo(@RequestParam int memberNo) {
        log.info(logTitleMsg + " 회원 정보 조회: memberNo = " + memberNo);

        return memberService.getMyPageInfo(memberNo);  // 조회 결과를 JSON 형태로 반환
    }



    @PutMapping("/myPage/update")
    public ResponseEntity<Map<String, String>> updateMemberInfo(@RequestBody MemberVo memberVo, HttpSession session) {
        Map<String, String> resultMap = new HashMap<>();
        log.info(logTitleMsg + " 회원 정보 수정: " + memberVo.toString());

        try {
            // 회원 정보 업데이트 수행
            memberService.updateMemberInfo(memberVo);

            // 세션에서 현재 사용자 정보 업데이트
            MemberVo currentMember = (MemberVo) session.getAttribute("member");
            if (currentMember != null) {
                currentMember.setUserName(memberVo.getUserName()); // 예: 이름 업데이트
                // 필요한 경우 다른 정보도 업데이트
                session.setAttribute("member", currentMember); // 세션에 업데이트된 회원 정보 저장
            }

            resultMap.put("status", "success");
            resultMap.put("alertMsg", "회원 정보가 성공적으로 수정되었습니다.");
            return ResponseEntity.ok(resultMap); // 성공 응답

        } catch (Exception e) {
            log.error(logTitleMsg + " 오류 발생: " + e.getMessage());
            resultMap.put("status", "error");
            resultMap.put("message", "회원 정보 수정 중 오류가 발생했습니다. " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap); // 오류 응답
        }
    }


    @PostMapping("/quit")
    public ResponseEntity<Map<String, String>> quitMember(@RequestBody MemberVo memberVo) {
        Map<String, String> resultMap = new HashMap<>();

        try {
            memberService.updateMemberQuitApply(memberVo); // 탈퇴 신청 처리
            resultMap.put("status", "success");
            resultMap.put("alertMsg", "탈퇴 신청이 완료되었습니다."); // alertMsg에 메시지 추가
            return ResponseEntity.ok(resultMap);  // JSON 응답으로 전송

        } catch (Exception e) {
            resultMap.put("status", "error");
            resultMap.put("alertMsg", "탈퇴 신청 중 오류가 발생했습니다."); // 오류 메시지 포함
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
        }
    }

}

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

            MemberVo currentMember = (MemberVo) session.getAttribute("member");

            //이메일 검증로직 중복시응답
            if (!memberVo.getEmail().equals(currentMember.getEmail())) {
                if (memberService.isEmailAlreadyRegistered(memberVo.getEmail())) {
                    resultMap.put("status", "failed");
                    resultMap.put("alertMsg", "이미 존재하는 이메일입니다.");
                    System.out.println("중복이메일발생: " + memberVo.getEmail());
                    return ResponseEntity.status(HttpStatus.CONFLICT).body(resultMap); // 이메일 중복 응답
                }
            }
            MemberVo updatedMemberVo =memberService.updateMemberInfo(memberVo); //업데이트
                updatedMemberVo.setGrade("MEMBER");
            log.info(logTitleMsg + " 회원 정보 수정: " + updatedMemberVo.toString());
                session.setAttribute("member", updatedMemberVo); // 세션 업데이트



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

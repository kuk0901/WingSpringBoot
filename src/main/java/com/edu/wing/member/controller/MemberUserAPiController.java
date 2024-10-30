package com.edu.wing.member.controller;

import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member/api/user")
public class MemberUserAPiController {
    private Logger log = LoggerFactory.getLogger(MemberUserAPiController.class);
    private final String logTitleMsg = "==MemberUserAPiController==";

    @Autowired
    private MemberService memberService;

    // 회원 정보 조회 API
    @GetMapping("/myPage/info")
    public MemberVo getMyPageInfo(@RequestParam int memberNo) {
        log.info(logTitleMsg + " 회원 정보 조회: memberNo = " + memberNo);
        return memberService.getMyPageInfo(memberNo);  // 조회 결과를 JSON 형태로 반환
    }
    // 회원 정보 수정 API
    @PutMapping("/myPage/update")
    public ResponseEntity<String> updateMemberInfo(@RequestBody MemberVo memberVo) {
        log.info(logTitleMsg + " 회원 정보 수정: " + memberVo.toString());

        try {
            memberService.updateMemberInfo(memberVo);
            return ResponseEntity.ok("회원 정보가 성공적으로 수정되었습니다."); // 성공 응답
        } catch (Exception e) {
            log.error(logTitleMsg + " 오류 발생: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("회원 정보 수정 중 오류가 발생했습니다." + e.getMessage()); // 오류 응답
        }
    }

    @PostMapping("/quit")
    public ResponseEntity<String> quitMember(@RequestBody MemberVo memberVo) {
        memberService.updateMemberQuitApply(memberVo);
        return ResponseEntity.ok("탈퇴 신청이 완료되었습니다.");
    }
}

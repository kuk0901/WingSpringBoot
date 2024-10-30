package com.edu.wing.member.controller;

import com.edu.wing.accountbook.service.AccountBookService;
import com.edu.wing.inquiry.service.InquiryService;
import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.member.service.MemberService;
import com.edu.wing.util.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/member")
public class AdminMemberApiController {
    private Logger log = LoggerFactory.getLogger(AdminMemberApiController.class);
    private final String logTitleMsg = "==AdminMemberApiController==";

    @Autowired
    private MemberService memberService;
    @Autowired
    private AccountBookService accountBookService;


    @GetMapping("/{memberNo}")
    public ResponseEntity<Map<String, Object>> selectMemberDetailForAdmin(@PathVariable int memberNo, @RequestParam int curPage) {
        log.info(logTitleMsg);
        log.info("@GetMapping memberDetail memberNo: {}, curPage: {}", memberNo, curPage);

        // 회원 상세 정보를 가져오는 서비스 메서드 호출
        Map<String, Object> resultMap = memberService.selectMemberDetailForAdmin(memberNo);

        // 현재 페이지 정보 추가
        resultMap.put("curPage", curPage);

        return ResponseEntity.ok(resultMap);
    }



    // 관리자 강제 회원 삭제
    @PatchMapping("/delete/{memberNo}")
    @ResponseBody
    public String adminDeleteMember(@PathVariable int memberNo) {
        log.info(logTitleMsg);
        log.info("@DeleteMapping memberNo: {}", memberNo);

        try {
            //1.가계부 내역 강제 삭제
            accountBookService.accountBookDelete(memberNo); // 가계부 삭제 호출
            //2.게시판 댓글,게시글 삭제 예정
            

            // 회원 삭제->isDeleted->'true'변경
            boolean result = memberService.adminDeleteMember(memberNo);
            return result ? "삭제 성공" : "삭제 실패";
        } catch (Exception e) {
            log.error("회원 삭제 중 오류 발생: {}", e);
            return "회원 삭제 중 오류 발생";
        }
    }


    // 관리자 마이페이지 GET 요청 처리
    @GetMapping("/mypage/{memberNo}")
    public ModelAndView getAdminMypage(@PathVariable("memberNo") int memberNo) {
        MemberVo memberInfo = memberService.getAdminMypageInfo(memberNo);

        ModelAndView mav = new ModelAndView("jsp/admin/user/adminMypage"); // JSP 파일 경로
        mav.addObject("memberInfo", memberInfo);
        return mav;
    }
    @PatchMapping("/update")
    public String updateMember(@RequestBody MemberVo memberVo) {
        int result = memberService.updateMember(memberVo);
        return result > 0 ? "회원 정보가 업데이트되었습니다." : "회원 정보 업데이트에 실패했습니다.";
    }


}

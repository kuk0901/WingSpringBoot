package com.edu.wing.member.controller;

import com.edu.wing.accountbook.service.AccountBookService;
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
@RequestMapping("/admin/api/member")
public class AdminMemberApiController {
    private Logger log = LoggerFactory.getLogger(AdminMemberApiController.class);
    private final String logTitleMsg = "==AdminMemberApiController==";

    @Autowired
    private MemberService memberService;
    @Autowired
    private AccountBookService accountBookService;

    private final String ALERT_MSG = "alertMsg";

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
        log.info("@PatchMapping memberNo: {}", memberNo);

        try {
            //1.가계부 내역 강제 삭제
            accountBookService.deleteAllAccountBook(memberNo); // 가계부 삭제 호출
            //2.게시판 댓글,게시글 삭제 예정



            // 3. 회원 삭제 -> isDeleted -> 'true' 변경
            MemberVo memberVo = new MemberVo();
            memberVo.setMemberNo(memberNo); // memberNo 설정
            memberService.adminSoftDeleteMember(memberVo); // 소프트 삭제 호출
            return "삭제 성공";
        } catch (Exception e) {
            log.error("회원 삭제 중 오류 발생: {}", e);
            return "회원 삭제 중 오류 발생";
        }
    }

  /*  @PatchMapping("/update")
    public String updateMember(@RequestBody MemberVo memberVo) {
        int result = memberService.updateMember(memberVo);
        return result > 0 ? "회원 정보가 업데이트되었습니다." : "회원 정보 업데이트에 실패했습니다.";
    }*/
  @PatchMapping("/update")
  public ResponseEntity<Map<String, String>> updateMember(@RequestBody MemberVo memberVo, HttpSession session) {
      Map<String, String> resultMap = new HashMap<>();

      try {
          int result = memberService.updateMember(memberVo);

          if (result > 0) {
              MemberVo currentMember = (MemberVo) session.getAttribute("member");
              // 현재 회원 정보가 존재하면 업데이트
              if (currentMember != null) {
                  currentMember.setUserName(memberVo.getUserName()); // 이름 업데이트
                  session.setAttribute("member", currentMember); // 세션 업데이트
              }
              resultMap.put("status", "success");
              resultMap.put("alertMsg", "회원 정보가 업데이트되었습니다.");
              return ResponseEntity.ok(resultMap);  // 성공 응답

          } else {
              resultMap.put("status", "fail");
              resultMap.put("message", "회원 정보 업데이트에 실패했습니다.");
              return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);  // 실패 응답
          }
      } catch (Exception e) {
          resultMap.put("status", "error");
          resultMap.put("message", "서버 오류가 발생했습니다.");
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);  // 오류 응답
      }
  }
}

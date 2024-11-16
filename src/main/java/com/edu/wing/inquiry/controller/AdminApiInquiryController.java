package com.edu.wing.inquiry.controller;

import com.edu.wing.inquiry.service.InquiryService;
import com.edu.wing.inquiryComment.service.InquiryCommentService;
import com.edu.wing.member.domain.MemberVo;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/cs/inquiry")
public class AdminApiInquiryController {

  private static final Logger log = LoggerFactory.getLogger(AdminApiInquiryController.class);
  private static final String LOG_TITLE = "==AdminApiInquiryController==";

  @Autowired
  private InquiryService inquiryService;

  @Autowired
  private InquiryCommentService inquiryCommentService;

  @GetMapping("/{inquiryNo}")
  public ResponseEntity<Map<String, Object>> inquiryDetail(@PathVariable int inquiryNo, @RequestParam(defaultValue = "1") int curPage
      , @RequestParam(defaultValue = "") String inquirySearch) {

    Map<String, Object> resultMap = new HashMap<>();

    Map<String, Object> inquiryVo = inquiryService.inquirySelectOne(inquiryNo);

    if (inquiryVo == null) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 해당 문의글을 불러 올 수 없습니다. 잠시 후 다시 시도해 주세요.");

      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("curPage", curPage);
    resultMap.put("inquirySearch", inquirySearch);
    resultMap.put("inquiryVo", inquiryVo);

    return ResponseEntity.ok().body(resultMap);
  }

  @GetMapping("/add/{inquiryNo}")
  public ResponseEntity<?> getInquiryForReply(@PathVariable int inquiryNo, HttpSession session, @RequestParam(defaultValue = "1") int curPage
      , @RequestParam(defaultValue = "") String inquirySearch) {

    Map<String, Object> resultMap = new HashMap<>();

    resultMap.put("curPage", curPage);
    resultMap.put("inquirySearch", inquirySearch);

    MemberVo member = (MemberVo) session.getAttribute("member");

    Map<String, Object> inquiryVo = inquiryService.inquirySelectOne(inquiryNo);

    if (inquiryVo == null) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 추가 페이지로 이동할 수 없습니다. 잠시 후 다시 시도해주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    inquiryVo.put("adminEmail", member.getEmail());

    resultMap.put("status", "success");
    resultMap.put("inquiryVo", inquiryVo);

    return ResponseEntity.ok().body(resultMap);
  }

  @GetMapping("/update/{inquiryNo}")
  public ResponseEntity<?> getInquiryForUpdate(@PathVariable int inquiryNo, @RequestParam(defaultValue = "1") int curPage
      , @RequestParam(defaultValue = "") String inquirySearch) {

    Map<String, Object> resultMap = new HashMap<>();

    resultMap.put("curPage", curPage);
    resultMap.put("inquirySearch", inquirySearch);

    Map<String, Object> inquiryVo = inquiryService.inquirySelectOne(inquiryNo);

    if (inquiryVo == null) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 정보를 불러올 수 없습니다. 잠시 후 다시 시도해주세요."); // 실패 메시지

      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("inquiryVo", inquiryVo);
    resultMap.put("alertMsg", "정보 불러오기에 성공했습니다."); // 성공 메시지

    return ResponseEntity.ok().body(resultMap);

  }

  @PatchMapping("/update/{inquiryCommentNo}")
  public ResponseEntity<?> updateInquiryComment(@PathVariable int inquiryCommentNo, @RequestParam int inquiryNo, @RequestBody Map<String, String> updateData
      , @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "") String inquirySearch) {

    Map<String, Object> resultMap = new HashMap<>();

    resultMap.put("curPage", curPage);
    resultMap.put("inquirySearch", inquirySearch);

    boolean updated = inquiryCommentService.updateInquiryComment(inquiryCommentNo, updateData.get("content"));

    if (!updated) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 답변이 수정되지 않았습니다. 잠시 후 다시 시도해 주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    Map<String, Object> inquiryVo = inquiryService.inquirySelectOne(inquiryNo);

    resultMap.put("status", "success");
    resultMap.put("inquiryVo", inquiryVo);
    resultMap.put("alertMsg", "답변이 수정되었습니다.");

    return ResponseEntity.ok().body(resultMap);
  }

  @PatchMapping("/add/{inquiryNo}")
  public ResponseEntity<?> addInquiryReply(@PathVariable int inquiryNo, @RequestBody Map<String, String> replyData
      , HttpSession session, @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "") String inquirySearch) {
    log.info(LOG_TITLE);
    log.info("addInquiryReply PATCH inquiryNo: {}, content: {}", inquiryNo, replyData.get("CONTENT"));

    Map<String, Object> resultMap = new HashMap<>();

    resultMap.put("curPage", curPage);
    resultMap.put("inquirySearch", inquirySearch);

    MemberVo member = (MemberVo) session.getAttribute("member");

    boolean added = inquiryService.addInquiryReply(inquiryNo, replyData.get("CONTENT"), member.getMemberNo());
    if (!added) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 답변을 추가할 수 없습니다. 잠시 후 다시 시도해 주세요");
      return ResponseEntity.badRequest().body(resultMap);
    }

    boolean updated = inquiryService.updateAnswerTermination(inquiryNo);
    if (!updated) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 답변의 상태가 변경되지 않았습니다. 잠시 후 다시 시도해 주세요");
      return ResponseEntity.badRequest().body(resultMap);
    }

    Map<String, Object> inquiryVo = inquiryService.inquirySelectOne(inquiryNo);

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "답변 추가에 성공했습니다");
    resultMap.put("inquiryVo", inquiryVo);
    return ResponseEntity.ok().body(resultMap);
  }


}

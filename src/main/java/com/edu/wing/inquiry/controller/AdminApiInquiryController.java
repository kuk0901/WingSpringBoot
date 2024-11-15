package com.edu.wing.inquiry.controller;

import com.edu.wing.inquiry.service.InquiryService;
import com.edu.wing.inquiryComment.service.InquiryCommentService;
import com.edu.wing.member.domain.MemberVo;
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
@RequestMapping("/admin/api/cs/inquiry")
public class AdminApiInquiryController {

  private static final Logger log = LoggerFactory.getLogger(AdminApiInquiryController.class);
  private static final String LOG_TITLE = "==AdminApiInquiryController==";

  @Autowired
  private InquiryService inquiryService;

  @Autowired
  private InquiryCommentService inquiryCommentService;

  @PatchMapping("/update/{inquiryCommentNo}")
  public ResponseEntity<?> updateInquiryComment(@PathVariable int inquiryCommentNo, @RequestParam int inquiryNo, @RequestBody Map<String, String> updateData
      , @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "") String inquirySearch, @RequestParam(defaultValue = "N") String answerTermination) {

    Map<String, Object> resultMap = new HashMap<>();

    resultMap.put("curPage", curPage);
    resultMap.put("inquirySearch", inquirySearch);
    resultMap.put("answerTermination", answerTermination);

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
  public ResponseEntity<?> addInquiryReply(@PathVariable int inquiryNo, @RequestBody Map<String, String> replyData, HttpSession session) {
    log.info(LOG_TITLE);
    log.info("addInquiryReply PATCH inquiryNo: {}, content: {}", inquiryNo, replyData.get("CONTENT"));

    Map<String, Object> resultMap = new HashMap<>();

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

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "답변 추가에 성공했습니다");
    return ResponseEntity.ok().body(resultMap);
  }


}

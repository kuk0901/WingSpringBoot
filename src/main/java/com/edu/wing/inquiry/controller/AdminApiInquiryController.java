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

  @PostMapping("/update/{inquiryNo}")
  public ResponseEntity<?> getInquiryForUpdate(@PathVariable int inquiryNo) {
    log.info(LOG_TITLE);
    log.info("getInquiryForUpdate POST inquiryNo: {}", inquiryNo);

    Map<String, Object> resultMap = new HashMap<>();

    try {
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

    } catch (Exception e) {
      log.error("Error fetching inquiry data for update", e);
      return ResponseEntity.internalServerError().body("Error fetching inquiry data");
    }
  }

  @PatchMapping("/update/{inquiryCommentNo}")
  public ResponseEntity<?> updateInquiryComment(@PathVariable int inquiryCommentNo, @RequestBody Map<String, String> updateData) {
    log.info(LOG_TITLE);
    log.info("updateInquiryComment PATCH inquiryCommentNo: {}, content: {}", inquiryCommentNo, updateData.get("content"));

    Map<String, String> resultMap = new HashMap<>();

    try {
      boolean updated = inquiryCommentService.updateInquiryComment(inquiryCommentNo, updateData.get("content"));

      if (!updated) {
        resultMap.put("status", "failed");
        resultMap.put("alertMsg", "서버 오류로 인해 답변이 수정되지 않았습니다. 잠시 후 다시 시도해 주세요.");
        return ResponseEntity.internalServerError().body(resultMap);
      }

      resultMap.put("status", "success");
      resultMap.put("alertMsg", "답변이 수정되었습니다.");
      return ResponseEntity.ok().body(resultMap);
    } catch (Exception e) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 답변이 수정되지 않았습니다. 잠시 후 다시 시도해 주세요.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
  }

  @PostMapping("/add/{inquiryNo}")
  public ResponseEntity<?> getInquiryForReply(@PathVariable int inquiryNo, HttpSession session) {
    log.info(LOG_TITLE);
    log.info("getInquiryForReply POST inquiryNo: {}", inquiryNo);

    Map<String, Object> resultMap = new HashMap<>();

    MemberVo member = (MemberVo) session.getAttribute("member");

    try {
      Map<String, Object> inquiryVo = inquiryService.inquirySelectOne(inquiryNo);
      if (resultMap == null) {
        resultMap.put("status", "failed");
        resultMap.put("alertMsg", "서버 오류로 인해 추가 페이지로 이동할 수 없습니다. 잠시 후 다시 시도해주세요.");
        return ResponseEntity.badRequest().body(resultMap);
      }

      inquiryVo.put("adminEmail", member.getEmail());

      resultMap.put("status", "success");
      resultMap.put("inquiryVo", inquiryVo);
      resultMap.put("alertMsg", "정보 불러오기에 성공했습니다.");

      return ResponseEntity.ok().body(resultMap);
    } catch (Exception e) {
      log.error("Error fetching inquiry data for reply", e);
      return ResponseEntity.internalServerError().body("Error fetching inquiry data");
    }
  }

  @PatchMapping("/add/{inquiryNo}")
  public ResponseEntity<?> addInquiryReply(@PathVariable int inquiryNo, @RequestBody Map<String, String> replyData, HttpSession session) {
    log.info(LOG_TITLE);
    log.info("addInquiryReply PATCH inquiryNo: {}, content: {}", inquiryNo, replyData.get("CONTENT"));

    Map<String, Object> resultMap = new HashMap<>();

    MemberVo member = (MemberVo) session.getAttribute("member");

    try {
      // 1. 답변 추가
      boolean added = inquiryService.addInquiryReply(inquiryNo, replyData.get("CONTENT"), member.getMemberNo());
      if (!added) {
        resultMap.put("status", "failed");
        resultMap.put("alertMsg", "서버 오류로 인해 답변을 추가할 수 없습니다. 잠시 후 다시 시도해 주세요");
        return ResponseEntity.badRequest().body(resultMap);
      }

      // 2. 답변 상태 업데이트
      boolean updated = inquiryService.updateAnswerTermination(inquiryNo);
      if (!updated) {
        resultMap.put("status", "failed");
        resultMap.put("alertMsg", "서버 오류로 인해 답변의 상태가 변경되지 않았습니다. 잠시 후 다시 시도해 주세요");
        return ResponseEntity.badRequest().body(resultMap);
      }

      resultMap.put("status", "success");
      resultMap.put("alertMsg", "답변 추가에 성공했습니다");
      return ResponseEntity.ok().body(resultMap);
    } catch (Exception e) {
      log.error("Error adding inquiry reply or updating status", e);
      return ResponseEntity.internalServerError().body("Error processing reply");
    }
  }

}

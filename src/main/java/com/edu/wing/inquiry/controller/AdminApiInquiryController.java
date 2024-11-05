package com.edu.wing.inquiry.controller;

import com.edu.wing.inquiry.service.InquiryService;
import com.edu.wing.member.domain.MemberVo;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/api/cs/inquiry")
public class AdminApiInquiryController {

  private static final Logger log = LoggerFactory.getLogger(AdminApiInquiryController.class);
  private static final String LOG_TITLE = "==AdminApiInquiryController==";

  @Autowired
  private InquiryService inquiryService;

  @GetMapping("/{inquiryNo}")
  public ResponseEntity<Map<String, Object>> inquiryDetail(@PathVariable int inquiryNo, @RequestParam int curPage) {
    log.info(LOG_TITLE);
    log.info("@RequestMapping inquiryDetail inquiryNo: {}, curPage: {}", inquiryNo, curPage);


    Map<String, Object> resultMap = inquiryService.inquirySelectOne(inquiryNo);

    if (resultMap == null) {
      return ResponseEntity.notFound().build();
    }

    resultMap.put("curPage", curPage);

    return ResponseEntity.ok().body(resultMap);
  }

  @PostMapping("/update/{inquiryNo}")
  public ResponseEntity<?> getInquiryForUpdate(@PathVariable int inquiryNo) {
    log.info(LOG_TITLE);
    log.info("getInquiryForUpdate POST inquiryNo: {}", inquiryNo);

    try {
      Map<String, Object> resultMap = inquiryService.inquirySelectOne(inquiryNo);
      if (resultMap != null && !resultMap.isEmpty()) {
        return ResponseEntity.ok().body(resultMap);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      log.error("Error fetching inquiry data for update", e);
      return ResponseEntity.internalServerError().body("Error fetching inquiry data");
    }
  }

  @PatchMapping("/update/{inquiryNo}")
  public ResponseEntity<?> updateInquiryComment(@PathVariable int inquiryNo, @RequestBody Map<String, String> updateData) {
    log.info(LOG_TITLE);
    log.info("updateInquiryComment PATCH inquiryNo: {}, content: {}", inquiryNo, updateData.get("CONTENT"));

    try {
      boolean updated = inquiryService.updateInquiryComment(inquiryNo, updateData.get("CONTENT"));
      if (updated) {
        return ResponseEntity.ok().body("Comment updated successfully");
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      log.error("Error updating inquiry comment", e);
      return ResponseEntity.internalServerError().body("Error updating comment");
    }
  }

  @PostMapping("/add/{inquiryNo}")
  public ResponseEntity<?> getInquiryForReply(@PathVariable int inquiryNo, HttpSession session) {
    log.info(LOG_TITLE);
    log.info("getInquiryForReply POST inquiryNo: {}", inquiryNo);

    MemberVo member = (MemberVo) session.getAttribute("member");

    try {
      Map<String, Object> resultMap = inquiryService.inquirySelectOne(inquiryNo);
      if (resultMap != null && !resultMap.isEmpty()) {
        resultMap.put("adminEmail", member.getEmail());
        return ResponseEntity.ok().body(resultMap);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      log.error("Error fetching inquiry data for reply", e);
      return ResponseEntity.internalServerError().body("Error fetching inquiry data");
    }
  }

  @PatchMapping("/add/{inquiryNo}")
  public ResponseEntity<?> addInquiryReply(@PathVariable int inquiryNo, @RequestBody Map<String, String> replyData, HttpSession session) {
    log.info(LOG_TITLE);
    log.info("addInquiryReply PATCH inquiryNo: {}, content: {}", inquiryNo, replyData.get("CONTENT"));

    MemberVo member = (MemberVo) session.getAttribute("member");
    if (member == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
    }

    try {
      boolean added = inquiryService.addInquiryReply(inquiryNo, replyData.get("CONTENT"), member.getMemberNo());
      if (added) {
        return ResponseEntity.ok().body("Reply added successfully");
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      log.error("Error adding inquiry reply", e);
      return ResponseEntity.internalServerError().body("Error adding reply");
    }
  }

}

package com.edu.wing.inquiry.controller;

import com.edu.wing.inquiry.service.InquiryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


}

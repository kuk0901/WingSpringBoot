package com.edu.wing.inquiry.controller;

import com.edu.wing.inquiry.domain.InquiryVo;
import com.edu.wing.inquiry.service.InquiryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/member/api/cs/inquiry")
public class MemberApiInquiryController {

  private static final Logger log = LoggerFactory.getLogger(MemberApiInquiryController.class);
  private static final String LOG_TITLE = "==MemberApiInquiryController==";

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

  @PostMapping("/add")
  public ResponseEntity<?> addInquiry(@RequestBody InquiryVo inquiryVo) {
    log.info(LOG_TITLE);
    log.info("addInquiry inquiryVo: {}", inquiryVo);

    try {
      inquiryService.addInquiry(inquiryVo);
      return ResponseEntity.ok().body("문의가 성공적으로 등록되었습니다");
    }catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문의 등록 중 오류가 발생했습니다");
    }
  }

}

package com.edu.wing.inquiry.controller;

import com.edu.wing.inquiry.domain.InquiryVo;
import com.edu.wing.inquiry.service.InquiryService;
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
@RequestMapping("/member/api/cs/inquiry")
public class MemberApiInquiryController {

  @Autowired
  private InquiryService inquiryService;

  @PostMapping("/list/add")
  public ResponseEntity<?> addInquiry(@RequestBody InquiryVo inquiryVo, HttpSession httpsSession) {

    Map<String, Object> resultMap = new HashMap<>();

    MemberVo member = (MemberVo) httpsSession.getAttribute("member");

    boolean addInquiry = inquiryService.addInquiry(inquiryVo);

    if(!addInquiry) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 문의 등록에 실패했습니다. 잠시 후 다시 시도해주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "문의 등록에 성공했습니다.");
    return ResponseEntity.ok().body("문의가 성공적으로 등록되었습니다");
  }
}

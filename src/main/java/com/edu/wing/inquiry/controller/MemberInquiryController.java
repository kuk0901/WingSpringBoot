package com.edu.wing.inquiry.controller;

import com.edu.wing.inquiry.domain.InquiryVo;
import com.edu.wing.inquiry.service.InquiryService;
import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.util.Paging;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member/cs/inquiry")
public class MemberInquiryController {

  @Autowired
  private InquiryService inquiryService;

  @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
  public ModelAndView inquiryList(@RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "") String inquirySearch, HttpSession httpSession) {

    int totalCount = inquiryService.inquirySelectTotalCount(inquirySearch);

    Paging pagingVo = new Paging(totalCount, curPage);
    int start = pagingVo.getPageBegin();
    int end = pagingVo.getPageEnd();

    MemberVo member = (MemberVo) httpSession.getAttribute("member");

    List<InquiryVo> inquiryList = inquiryService.memberInquirySelectList(start, end, member.getMemberNo());

    Map<String, Object> pagingMap = new HashMap<>();
    pagingMap.put("totalCount", totalCount);
    pagingMap.put("pagingVo", pagingVo);

    ModelAndView mav = new ModelAndView("jsp/member/inquiry/InquiryListView");

    mav.addObject("inquiryList", inquiryList);
    mav.addObject("pagingMap", pagingMap);
    mav.addObject("member", member);

    return mav;
  }

  @GetMapping("/list/add")
  public ModelAndView inquiryAdd(HttpSession httpSession) {

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = currentDate.format(formatter);

    // 세션에서 사용자 정보를 가져오기
    MemberVo member = (MemberVo) httpSession.getAttribute("member");

    // ModelAndView 생성 및 데이터 추가
    ModelAndView mav = new ModelAndView("jsp/member/inquiry/InquiryAddView"); // 경로 수정
    mav.addObject("currentDate", formattedDate); // 현재 날짜 추가
    mav.addObject("member", member); // 사용자 정보 추가

    return mav;
  }

}

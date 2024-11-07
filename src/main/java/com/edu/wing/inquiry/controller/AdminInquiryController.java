package com.edu.wing.inquiry.controller;

import com.edu.wing.inquiry.domain.InquiryVo;
import com.edu.wing.inquiry.service.InquiryService;
import com.edu.wing.util.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/cs/inquiry")
public class AdminInquiryController {

  private static final Logger log = LoggerFactory.getLogger(AdminInquiryController.class);
  private static final String LOG_TITLE = "==AdminInquiryController==";

  @Autowired
  private InquiryService inquiryService;

  @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
  public ModelAndView inquiryList(@RequestParam(defaultValue = "1") String curPage, @RequestParam(defaultValue = "") String inquirySearch) {
    log.info(LOG_TITLE);
    log.info("@RequestMapping categoryList curPage: {}", curPage);

    int totalCount = inquiryService.inquirySelectTotalCount(inquirySearch);

    Paging pagingVo = new Paging(totalCount, Integer.parseInt(curPage));
    int start = pagingVo.getPageBegin();
    int end = pagingVo.getPageEnd();

    List<InquiryVo> inquiryList = inquiryService.inquirySelectList(start, end, inquirySearch);

    log.info("inquiryList: {}", inquiryList);

    Map<String, Object> pagingMap = new HashMap<>();
    pagingMap.put("totalCount", totalCount);
    pagingMap.put("pagingVo", pagingVo);
    pagingMap.put("curPage", curPage);

    ModelAndView mav = new ModelAndView("jsp/admin/inquiry/InquiryListView");

    mav.addObject("inquiryList", inquiryList);
    mav.addObject("pagingMap", pagingMap);
    mav.addObject("inquirySearch", inquirySearch);

    return mav;
  }

  @GetMapping("/{inquiryNo}")
  public ResponseEntity<Map<String, Object>> inquiryDetail(@PathVariable int inquiryNo, @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "") String inquirySearch,
    @RequestParam(defaultValue = "N") String answerTermination) {
    log.info(LOG_TITLE);
    log.info("@RequestMapping inquiryDetail inquiryNo: {}, curPage: {}, inquirySearch: {}, answerTermination: {}", inquiryNo, curPage, inquirySearch, answerTermination);

    Map<String, Object> resultMap = new HashMap<>();

    Map<String, Object> inquiryVo = inquiryService.inquirySelectOne(inquiryNo);

    if (inquiryVo == null) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 정보를 불러 올 수 없습니다. 잠시 후 다시 시도해 주세요.");

      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("curPage", curPage);
    resultMap.put("inquirySearch", inquirySearch);
    resultMap.put("answerTermination", answerTermination);
    resultMap.put("inquiryVo", inquiryVo);

    return ResponseEntity.ok().body(resultMap);
  }

}

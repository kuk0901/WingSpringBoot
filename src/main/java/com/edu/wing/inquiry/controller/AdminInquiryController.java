package com.edu.wing.inquiry.controller;

import com.edu.wing.inquiry.domain.InquiryVo;
import com.edu.wing.inquiry.service.InquiryService;
import com.edu.wing.util.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
  public ModelAndView inquiryList(@RequestParam(defaultValue = "1") int curPage) {
    log.info(LOG_TITLE);
    log.info("@RequestMapping categoryList curPage: {}", curPage);

    int totalCount = inquiryService.inquirySelectTotalCount();

    Paging pagingVo = new Paging(totalCount, curPage);
    int start = pagingVo.getPageBegin();
    int end = pagingVo.getPageEnd();

    List<InquiryVo> inquiryList = inquiryService.inquirySelectList(start, end);

    Map<String, Object> pagingMap = new HashMap<>();
    pagingMap.put("totalCount", totalCount);
    pagingMap.put("pagingVo", pagingVo);

    ModelAndView mav = new ModelAndView("jsp/admin/inquiry/InquiryListView");

    mav.addObject("inquiryList", inquiryList);
    mav.addObject("pagingMap", pagingMap);

    return mav;
  }
}

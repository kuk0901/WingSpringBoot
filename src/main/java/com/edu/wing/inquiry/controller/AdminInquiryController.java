package com.edu.wing.inquiry.controller;

import com.edu.wing.inquiry.domain.InquiryVo;
import com.edu.wing.inquiry.service.InquiryService;
import com.edu.wing.util.Paging;
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

  @Autowired
  private InquiryService inquiryService;

  @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
  public ModelAndView inquiryList(@RequestParam(defaultValue = "1") String curPage, @RequestParam(defaultValue = "") String inquirySearch,
                                  @RequestParam(defaultValue = "N") String answerTermination) {

    int totalCount = inquiryService.inquirySelectTotalCount(inquirySearch);

    Paging pagingVo = new Paging(totalCount, Integer.parseInt(curPage));
    int start = pagingVo.getPageBegin();
    int end = pagingVo.getPageEnd();

    List<InquiryVo> inquiryList = inquiryService.inquirySelectList(start, end, inquirySearch);

    Map<String, Object> pagingMap = new HashMap<>();
    pagingMap.put("totalCount", totalCount);
    pagingMap.put("pagingVo", pagingVo);
    pagingMap.put("curPage", curPage);

    ModelAndView mav = new ModelAndView("jsp/admin/inquiry/InquiryListView");

    mav.addObject("inquiryList", inquiryList);
    mav.addObject("pagingMap", pagingMap);
    mav.addObject("answerTermination", answerTermination);
    mav.addObject("inquirySearch", inquirySearch);

    return mav;
  }

}

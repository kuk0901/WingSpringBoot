package com.edu.wing.sellingCard.controller;

import com.edu.wing.sellingCard.domain.SellingCardVo;
import com.edu.wing.sellingCard.service.SellingCardService;
import com.edu.wing.util.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/salesDashboard")
public class AdminSellingCardController {
  @Autowired
  private SellingCardService sellingCardService;

  @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
  public ModelAndView salesDashboardList(@RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "0") int cardNo
      , @RequestParam(defaultValue = "all") String termination) {
    int totalCount = sellingCardService.sellingCardSelectTotalCount(cardNo, termination);

    Paging pagingVo = new Paging(totalCount, curPage);
    int start = pagingVo.getPageBegin();
    int end = pagingVo.getPageEnd();

    List<SellingCardVo> sellingCardVoList = sellingCardService.sellingCardSelectList(start, end, cardNo, termination);

    Map<String, Object> pagingMap = new HashMap<>();
    pagingMap.put("totalCount", totalCount);
    pagingMap.put("pagingVo", pagingVo);

    ModelAndView mav = new ModelAndView("jsp/admin/card/SalesDashboardListView");
    mav.addObject("sellingCardVoList", sellingCardVoList);
    mav.addObject("pagingMap", pagingMap);
    mav.addObject("cardNo", cardNo);
    mav.addObject("termination", termination);

    return mav;
  }

}

package com.edu.wing.sellingCard.controller;

import com.edu.wing.sellingCard.domain.SellingCardVo;
import com.edu.wing.sellingCard.service.SellingCardService;
import com.edu.wing.util.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/salesDashboard")
public class AdminSellingCardController {
  private final Logger log = LoggerFactory.getLogger(AdminSellingCardController.class);
  private final String logTitleMsg = "==AdminSellingCardController==";

  @Autowired
  private SellingCardService sellingCardService;

  @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
  public ModelAndView salesDashboardList(@RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "0") int cardNo) {
    log.info(logTitleMsg);
    log.info("@RequestMapping salesDashboardList curPage: {}", curPage);

    int totalCount = sellingCardService.sellingCardSelectTotalCount(cardNo);

    Paging pagingVo = new Paging(totalCount, curPage);
    int start = pagingVo.getPageBegin();
    int end = pagingVo.getPageEnd();

    List<SellingCardVo> sellingCardVoList = sellingCardService.sellingCardSelectList(start, end, cardNo);

    Map<String, Object> pagingMap = new HashMap<>();
    pagingMap.put("totalCount", totalCount);
    pagingMap.put("pagingVo", pagingVo);

    ModelAndView mav = new ModelAndView("jsp/admin/card/SalesDashboardListView");
    mav.addObject("sellingCardVoList", sellingCardVoList);
    mav.addObject("pagingMap", pagingMap);
    mav.addObject("cardNo", cardNo);

    return mav;
  }

  @GetMapping("/{sellingCardNo}")
  public ResponseEntity<Map<String, Object>> sellingCardDetail(@PathVariable int sellingCardNo, @RequestParam int curPage, @RequestParam int cardNo) {
    log.info(logTitleMsg);
    log.info("@RequestMapping salesDashboardDetail sellingCardNo: {}, curPage: {}", sellingCardNo, curPage);

    Map<String, Object> resultMap = sellingCardService.sellingCardSelectOne(sellingCardNo);
    resultMap.put("curPage", curPage);
    resultMap.put("cardNo", cardNo);

    return ResponseEntity.ok().body(resultMap);
  }
}

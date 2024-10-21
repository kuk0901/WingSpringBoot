package com.edu.wing.card.controller;

import com.edu.wing.card.domain.CardVo;
import com.edu.wing.card.service.CardService;
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
@RequestMapping("/admin/api/productManagement")
public class AdminCardController {
  private final Logger log = LoggerFactory.getLogger(AdminCardController.class);
  private final String logTitleMsg = "==AdminCardController==";

  @Autowired
  private CardService cardService;

  // FIXME: 수정 필요
  @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
  public ModelAndView productList(@RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "all") String categoryName) {
    log.info(logTitleMsg);
    log.info("@RequestMapping salesDashboardList curPage: {}", curPage);

    int totalCount = cardService.cardSelectTotalCount(categoryName);

    Paging pagingVo = new Paging(totalCount, curPage);
    pagingVo.setPageScale(3);
    int start = pagingVo.getPageBegin();
    int end = pagingVo.getPageEnd();

    List<CardVo> cardList = cardService.cardSelectList(start, end, categoryName);

    Map<String, Object> pagingMap = new HashMap<>();
    pagingMap.put("totalCount", totalCount);
    pagingMap.put("pagingVo", pagingVo);

    ModelAndView mav = new ModelAndView("jsp/admin/card/PaymentManagementListView");
    mav.addObject("cardList", cardList);
    mav.addObject("pagingMap", pagingMap);
    mav.addObject("categoryName", categoryName);

    return mav;
  }

  // FIXME: 수정 필요
  @GetMapping("/{cardNo}")
  // ResponseEntity<Map<String, Object>>
  public ResponseEntity<?> productDetail(@PathVariable int cardNo, @RequestParam int curPage) {
    log.info(logTitleMsg);
    log.info("@RequestMapping salesDashboardDetail cardNo: {}", cardNo);

//    Map<String, Object> resultMap = cardService.sellingCardSelectOne(sellingCardNo);
//    resultMap.put("curPage", curPage);
//    resultMap.put("cardNo", cardNo);
//
//    return ResponseEntity.ok().body(resultMap);
    return ResponseEntity.ok("123");
  }
}

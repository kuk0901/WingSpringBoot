package com.edu.wing.sellingCard.controller;

import com.edu.wing.sellingCard.service.SellingCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/api/salesDashboard")
public class AdminApiSellingCardController {
  private final Logger log = LoggerFactory.getLogger(AdminApiSellingCardController.class);
  private final String logTitleMsg = "==AdminSellingCardController==";

  @Autowired
  private SellingCardService sellingCardService;

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

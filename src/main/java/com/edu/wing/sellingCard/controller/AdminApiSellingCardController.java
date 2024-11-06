package com.edu.wing.sellingCard.controller;

import com.edu.wing.sellingCard.service.SellingCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/api/salesDashboard")
public class AdminApiSellingCardController {
  @Autowired
  private SellingCardService sellingCardService;

  @GetMapping("/{sellingCardNo}")
  public ResponseEntity<Map<String, Object>> sellingCardDetail(@PathVariable int sellingCardNo, @RequestParam int curPage, @RequestParam int cardNo, @RequestParam String termination) {
    Map<String, Object> resultMap = sellingCardService.sellingCardSelectOne(sellingCardNo);
    resultMap.put("curPage", curPage);
    resultMap.put("cardNo", cardNo);
    resultMap.put("termination", termination);

    return ResponseEntity.ok().body(resultMap);
  }
}

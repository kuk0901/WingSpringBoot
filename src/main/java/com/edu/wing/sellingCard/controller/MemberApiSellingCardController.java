package com.edu.wing.sellingCard.controller;

import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.sellingCard.domain.SellingCardVo;
import com.edu.wing.sellingCard.service.SellingCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member/api/sellingCard")
public class MemberApiSellingCardController {
  private final Logger log = LoggerFactory.getLogger(MemberApiSellingCardController.class);
  private final String logTitleMsg = "==MemberApiSellingCardController==";

  @Autowired
  private SellingCardService sellingCardService;

  @PostMapping("/purchase")
  public ResponseEntity<Map<String, Object>> memberCardPurchase(@RequestBody Map<String, Object> requestData) {
    log.info(logTitleMsg);
    log.info("@RequestMapping salesDashboardDetail requestData: {}", requestData);
    Map<String, Object> resultMap = new HashMap<>();

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      SellingCardVo sellingCardVo = objectMapper.convertValue(requestData.get("sellingCardVo"), SellingCardVo.class);
      AccountBookVo accountBookVo = objectMapper.convertValue(requestData.get("accountBookVo"), AccountBookVo.class);

      if (sellingCardService.memberSellingCardExist(sellingCardVo)) {
        resultMap.put("status", "failed");
        resultMap.put("alertMsg", "현재 사용 중인 WING_ 카드가 존재합니다. 다른 카드 사용을 원하신다면 기존 카드를 해지하셔야 합니다.");

        return ResponseEntity.badRequest().body(resultMap);
      }

      resultMap = sellingCardService.processMemberCardPurchase(sellingCardVo, accountBookVo);

      if ("success".equals(resultMap.get("status"))) {
        return ResponseEntity.ok().body(resultMap);
      } else {
        return ResponseEntity.badRequest().body(resultMap);
      }
    } catch (Exception e) {
      log.error(logTitleMsg, e);

      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "카드 구매 처리 중 오류가 발생했습니다. 관리자에게 문의해 주세요.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
  }

}

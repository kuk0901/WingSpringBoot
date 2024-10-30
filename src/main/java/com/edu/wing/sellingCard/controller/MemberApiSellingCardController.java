package com.edu.wing.sellingCard.controller;

import com.edu.wing.cardBenefit.domain.CardBenefitVo;
import com.edu.wing.cardBenefit.service.CardBenefitService;
import com.edu.wing.sellingCard.service.SellingCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member/api/salesDashboard")
public class MemberApiSellingCardController {
  private final Logger log = LoggerFactory.getLogger(MemberApiSellingCardController.class);
  private final String logTitleMsg = "==MemberApiSellingCardController==";

  @Autowired
  private SellingCardService sellingCardService;
  @Autowired
  private CardBenefitService cardBenefitService;

  @GetMapping("/sellingCards/{memberNo}")
  public List<Map<String, Object>> getSellingCards(@PathVariable int memberNo) {
    log.info("{} getSellingCards() 호출 - memberNo: {}", logTitleMsg, memberNo);

    // 판매 카드 정보를 가져옴
    List<Map<String, Object>> sellingCards = sellingCardService.sellingCardSelectOneForUserPage(memberNo);
    log.info("{} 판매 카드 정보 반환 - count: {}", logTitleMsg, sellingCards.size());

    return sellingCards;
  }

  // 카드 혜택 정보를 가져오는 메소드 추가
  @GetMapping("/cardBenefit/{cardNo}")
  public ResponseEntity<List<CardBenefitVo>> getCardBenefits(@PathVariable int cardNo) {
    try {
      List<CardBenefitVo> benefits = cardBenefitService.cardBenefitSelectListOne(cardNo);
      if (benefits == null || benefits.isEmpty()) {
        return ResponseEntity.notFound().build();
      }
      return ResponseEntity.ok(benefits);
    } catch (Exception e) {
      log.error("Error fetching card benefits for cardNo: {}", cardNo, e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 오류 반환
    }
  }

}



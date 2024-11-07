package com.edu.wing.sellingCard.controller;

import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.cardBenefit.domain.CardBenefitVo;
import com.edu.wing.cardBenefit.service.CardBenefitService;
import com.edu.wing.sellingCard.domain.SellingCardVo;
import com.edu.wing.sellingCard.service.SellingCardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member/api/sellingCard")
public class MemberApiSellingCardController {
  @Autowired
  private SellingCardService sellingCardService;
  
  @Autowired
  private CardBenefitService cardBenefitService;

  // 일반 카드 구매
  @PostMapping("/purchase/general")
  public ResponseEntity<Map<String, Object>> memberCardPurchase(@RequestBody Map<String, Object> requestData) {
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
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "카드 구매 처리 중 오류가 발생했습니다. 관리자에게 문의해 주세요.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
  }

  @GetMapping("/purchase/{memberNo}")
  public List<Map<String, Object>> getSellingCards(@PathVariable int memberNo) {
    List<Map<String, Object>> sellingCards = sellingCardService.sellingCardSelectOneForUserPage(memberNo);

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
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 오류 반환
    }
  }
  // 카드 소프트 삭제를 위한 메소드
  @PostMapping("/cardSoftDelete/{memberNo}")
  public ResponseEntity<Map<String, Object>> softDeleteCard(@PathVariable int memberNo) {
    try {
      Map<String, Object> resultMap = new HashMap<>();

      int result = sellingCardService.deleteCardSoft(memberNo);
      if (result > 0) {
        resultMap.put("alertMsg", "카드해지를 성공하셨습니다");
        return ResponseEntity.ok(resultMap); // 200 OK
      } else {
        resultMap.put("alertMsg", "카드해지를 실패하셨습니다 고객센터에 문의해주세요");
        return ResponseEntity.badRequest().body(resultMap); // 404 Not Found
      }
    } catch (Exception e) {

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
    }
  }

  // FIXME: 추천 카드 구매
  @PostMapping("/purchase/recommend")
  public ResponseEntity<Map<String, Object>> memberRecommendedCardPurchase(@RequestBody Map<String, Object> requestData) {
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

      resultMap = sellingCardService.processMemberRecommendedCardPurchase(sellingCardVo, accountBookVo);

      if ("success".equals(resultMap.get("status"))) {
        return ResponseEntity.ok().body(resultMap);
      } else {
        return ResponseEntity.badRequest().body(resultMap);
      }
    } catch (Exception e) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "카드 구매 처리 중 오류가 발생했습니다. 관리자에게 문의해 주세요.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
  }

}




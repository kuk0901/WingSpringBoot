package com.edu.wing.sellingCard.controller;

import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.cardBenefit.domain.CardBenefitVo;
import com.edu.wing.cardBenefit.service.CardBenefitService;
import com.edu.wing.sellingCard.domain.SellingCardVo;
import com.edu.wing.sellingCard.service.SellingCardService;
import com.edu.wing.util.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

      return ResponseEntity.ok().body(resultMap);

    } catch (RuntimeException e) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", e.getMessage());
      return ResponseEntity.badRequest().body(resultMap);
    } catch (Exception e) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "카드 구매 처리 중 오류가 발생했습니다. 관리자에게 문의해 주세요.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
  }

  // 카드 소프트 삭제를 위한 메소드
  @DeleteMapping("/cardSoftDelete/{memberNo}")
  public ResponseEntity<Map<String, String>> softDeleteCard(@PathVariable int memberNo, @RequestBody Map<String, Object> cardInfo) {
    Map<String, String> resultMap = new HashMap<>();

    try {
      sellingCardService.deleteCardSoft(cardInfo, memberNo);

      resultMap.put("status", "success");
      resultMap.put("alertMsg", "회원님의 카드가 해지되었습니다. 더 나은 WING_ 카드가 되기 위해 노력하겠습니다.");

      return ResponseEntity.ok().body(resultMap);
    } catch (CustomException e) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", e.getMessage());
      return ResponseEntity.badRequest().body(resultMap);
    } catch (Exception e) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 카드 해지에 실패했습니다. 고객센터로 문의해 주세요.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
  }

  @GetMapping("/purchase/{memberNo}")
  public ResponseEntity<?> getSellingCards(@PathVariable int memberNo) {
    Map<String, Object> sellingCard = sellingCardService.sellingCardSelectOneForUserPage(memberNo);
    int cardNo = ((BigDecimal) sellingCard.get("CARDNO")).intValue();
    List<CardBenefitVo> benefits = cardBenefitService.cardBenefitSelectListOne(cardNo);

    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("sellingCard", sellingCard);
    resultMap.put("benefits", benefits);

    return ResponseEntity.ok().body(resultMap);
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




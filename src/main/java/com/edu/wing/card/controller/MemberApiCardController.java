package com.edu.wing.card.controller;

import com.edu.wing.card.domain.CardVo;
import com.edu.wing.card.service.CardService;
import com.edu.wing.cardBenefit.domain.CardBenefitVo;
import com.edu.wing.cardBenefit.service.CardBenefitService;
import com.edu.wing.sellingCard.service.SellingCardService;
import com.edu.wing.util.CardBenefitUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/member/api/product")
public class MemberApiCardController {
  private final Logger log = LoggerFactory.getLogger(MemberApiCardController.class);

  private final String STATUS = "status";
  private final String STATUS_SUCCESS = "success";

  @Autowired
  private CardService cardService;

  @Autowired
  private CardBenefitService cardBenefitService;

  @Autowired
  private SellingCardService sellingCardService;

  @GetMapping("/card-detail/{cardNo}")
  public ResponseEntity<?> productDetail(HttpSession session, @PathVariable("cardNo") String cardNo, @RequestParam(defaultValue = "1") int curPage
      , @RequestParam(defaultValue = "all") String categoryName, HttpServletRequest request) {
    log.info("@GetMapping productDetail cardNo: {}, curPage: {}, categoryName: {}", cardNo, curPage, categoryName);

    CardVo cardVo = cardService.cardSelectOne(Integer.parseInt(cardNo));

    List<CardBenefitVo> allBenefits = cardBenefitService.cardBenefitSelectList();
    List<CardBenefitVo> benefitList =  CardBenefitUtil.filterBenefitsForCard(cardVo, allBenefits);

    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put(STATUS, STATUS_SUCCESS);
    resultMap.put("member", session.getAttribute("member"));
    resultMap.put("cardVo", cardVo);
    resultMap.put("benefitList", benefitList);
    resultMap.put("curPage", curPage);
    resultMap.put("categoryName", categoryName);

    return ResponseEntity.ok().body(resultMap);
  }

}

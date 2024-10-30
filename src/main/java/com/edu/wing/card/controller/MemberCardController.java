package com.edu.wing.card.controller;

import com.edu.wing.card.domain.CardVo;
import com.edu.wing.card.service.CardService;
import com.edu.wing.cardBenefit.domain.CardBenefitVo;
import com.edu.wing.cardBenefit.service.CardBenefitService;
import com.edu.wing.util.CardBenefitUtil;
import com.edu.wing.util.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member/product")
public class MemberCardController {
  private final Logger log = LoggerFactory.getLogger(MemberCardController.class);

  @Autowired
  private CardService cardService;

  @Autowired
  private CardBenefitService cardBenefitService;

  // controller로 수정 필요
  @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
  public ModelAndView productList(@RequestParam(defaultValue = "1") String curPage, @RequestParam(defaultValue = "all") String categoryName) {
    log.info("@RequestMapping productList curPage: {}", curPage);

    int totalCount = cardService.cardSelectTotalCount(categoryName);

    Paging pagingVo = new Paging(totalCount, Integer.parseInt(curPage), 3);
    int start = pagingVo.getPageBegin();
    int end = pagingVo.getPageEnd();

    List<CardVo> cardList = cardService.cardSelectList(start, end, categoryName);

    log.info("cardList: {}", cardList);

    List<CardBenefitVo> allBenefits = cardBenefitService.cardBenefitSelectList();

    Map<Integer, List<CardBenefitVo>> benefitMap = CardBenefitUtil.filterBenefitsForCards(cardList, allBenefits);

    Map<String, Object> pagingMap = new HashMap<>();
    pagingMap.put("totalCount", totalCount);
    pagingMap.put("pagingVo", pagingVo);

    ModelAndView mav = new ModelAndView("jsp/member/card/ProductListView");
    mav.addObject("cardList", cardList);
    mav.addObject("benefitMap", benefitMap);
    mav.addObject("pagingMap", pagingMap);
    mav.addObject("categoryName", categoryName);

    return mav;
  }

}

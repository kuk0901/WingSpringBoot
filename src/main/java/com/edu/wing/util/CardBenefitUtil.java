package com.edu.wing.util;

import com.edu.wing.card.domain.CardVo;
import com.edu.wing.cardBenefit.domain.CardBenefitVo;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CardBenefitUtil {

  // List<CardVo> 객체에 대한 혜택 필터링 메서드
  public static Map<Integer, List<CardBenefitVo>> filterBenefitsForCards(List<CardVo> cardList, List<CardBenefitVo> allBenefits) {
    Set<Integer> cardNumbers = cardList.stream()
        .map(CardVo::getCardNo)
        .collect(Collectors.toSet());

    return allBenefits.stream()
        .filter(benefit -> cardNumbers.contains(benefit.getCardNo()))
        .collect(Collectors.groupingBy(CardBenefitVo::getCardNo));
  }

  // 단일 CardVo 객체에 대한 혜택 필터링 메서드
  public static List<CardBenefitVo> filterBenefitsForCard(CardVo card, List<CardBenefitVo> allBenefits) {
    int cardNumber = card.getCardNo();

    return allBenefits.stream()
        .filter(benefit -> benefit.getCardNo() == cardNumber)
        .collect(Collectors.toList());
  }

}
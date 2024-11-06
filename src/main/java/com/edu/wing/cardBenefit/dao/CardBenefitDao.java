package com.edu.wing.cardBenefit.dao;

import com.edu.wing.cardBenefit.domain.CardBenefitVo;

import java.util.List;

public interface CardBenefitDao {
  List<CardBenefitVo> cardBenefitSelectList();
  List<CardBenefitVo> cardBenefitSelectListOne(int cardNo);
  CardBenefitVo cardBenefitSelectOne(int cardNo);
  void cardBenefitInsertOne(CardBenefitVo cardBenefitVo);
  List<CardBenefitVo> userAccountBookDiscountRateList(int memberNo);
}

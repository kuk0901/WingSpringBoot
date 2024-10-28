package com.edu.wing.cardBenefit.service;

import com.edu.wing.cardBenefit.domain.CardBenefitVo;

import java.util.List;

public interface CardBenefitService {
  List<CardBenefitVo> cardBenefitSelectList();
  CardBenefitVo cardBenefitSelectOne(int cardNo);
  List<CardBenefitVo> cardBenefitSelectListOne(int cardNo);
}

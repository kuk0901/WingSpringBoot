package com.edu.wing.cardBenefit.service;

import com.edu.wing.cardBenefit.dao.CardBenefitDao;
import com.edu.wing.cardBenefit.domain.CardBenefitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardBenefitServiceImpl implements CardBenefitService {

  @Autowired
  private CardBenefitDao cardBenefitDao;

  @Override
  public List<CardBenefitVo> cardBenefitSelectList() {
    return cardBenefitDao.cardBenefitSelectList();
  }

  @Override
  public CardBenefitVo cardBenefitSelectOne(int cardNo) {
    return cardBenefitDao.cardBenefitSelectOne(cardNo);
  }

  @Override
  public List<CardBenefitVo> cardBenefitSelectListOne(int cardNo) {
    return cardBenefitDao.cardBenefitSelectListOne(cardNo);
  }
}

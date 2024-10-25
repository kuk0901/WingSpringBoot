package com.edu.wing.cardBenefit.dao;

import com.edu.wing.cardBenefit.domain.CardBenefitVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardBenefitDaoImpl implements CardBenefitDao {

  @Autowired
  private SqlSession sqlSession;

  private final String namespace = "com.edu.wing.cardBenefit.";
  
  @Override
  public List<CardBenefitVo> cardBenefitSelectList() {
    return sqlSession.selectList(namespace + "cardBenefitSelectList");
  }

  @Override
  public CardBenefitVo cardBenefitSelectOne(int cardNo) {
    return sqlSession.selectOne(namespace + "cardBenefitSelectOne", cardNo);
  }

  @Override
  public void cardBenefitInsertOne(CardBenefitVo cardBenefitVo) {
    sqlSession.insert(namespace + "cardBenefitInsertOne", cardBenefitVo);
  }
}

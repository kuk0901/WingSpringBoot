package com.edu.wing.sellingCard.dao;

import com.edu.wing.sellingCard.domain.SellingCardVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SellingCardDaoImpl implements SellingCardDao {
  @Autowired
  private SqlSession sqlSession;

  private final String namespace = "com.edu.wing.sellingCard.";

  @Override
  public List<SellingCardVo> sellingCardSelectList(Map<String, Object> map) {
    return sqlSession.selectList(namespace + "sellingCardSelectList", map);
  }

  @Override
  public int sellingCardSelectTotalCount(Map<String, Object> map) {
    return sqlSession.selectOne(namespace + "sellingCardSelectTotalCount", map);
  }

  @Override
  public Map<String, Object> sellingCardSelectOne(int sellingCardNo) {
    return sqlSession.selectOne(namespace + "sellingCardSelectOne", sellingCardNo);
  }

  @Override
  public int countActiveSellingCardsByCardNo(int cardNo) {
    return sqlSession.selectOne(namespace + "countActiveSellingCardsByCardNo", cardNo);
  }

  @Override
  public List<SellingCardVo> memberSellingCardExist(SellingCardVo sellingCardVo) {
    return sqlSession.selectList(namespace + "memberSellingCardExist", sellingCardVo);
  }

  @Override
  public void memberPurchaseCard(SellingCardVo sellingCardVo) {
    sqlSession.insert(namespace + "memberPurchaseCard", sellingCardVo);
  }

  @Override
  public SellingCardVo memberPurchaseCardCheck(SellingCardVo sellingCardVo) {
    return sqlSession.selectOne(namespace + "memberPurchaseCardCheck", sellingCardVo);
  }

  @Override
  public Map<String, Object> sellingCardSelectOneForUserPage(int memberNo) {
    return sqlSession.selectOne(namespace + "sellingCardSelectOneForUserPage", memberNo);
  }

  @Override
  public void deleteCardSoft(Map<String, Object> cardInfo) {
    sqlSession.update(namespace + "deleteCardSoft", cardInfo);
  }

  @Override
  public SellingCardVo deleteCardSoftCheck(Map<String, Object> cardInfo) {
    return sqlSession.selectOne(namespace + "deleteCardSoftCheck", cardInfo);
  }

  @Override
  public List<HashMap<String, Object>> totalCardsSoldLast5Years() {
    return sqlSession.selectList(namespace + "totalCardsSoldLast5Years");
  }

  @Override
  public List<HashMap<String, Object>> recommendedCardsPurchasedLast5Years() {
    return sqlSession.selectList(namespace + "recommendedCardsPurchasedLast5Years");
  }

  @Override
  public List<HashMap<String, Object>> terminatedCardsLast5Years() {
    return sqlSession.selectList(namespace + "terminatedCardsLast5Years");
  }

  @Override
  public void memberPurchaseRecommendedCard(SellingCardVo sellingCardVo) {
    sqlSession.insert(namespace + "memberPurchaseRecommendedCard", sellingCardVo);
  }

  @Override
  public boolean checkSellingCardExists(Map<String, Object> params) {
    Integer count = sqlSession.selectOne(namespace+"checkSellingCardExists", params);
    return count != null && count > 0;
  }

  @Override
  public void updateDeleteCard(int memberNo) {
    sqlSession.update(namespace+"updateDeleteCard",memberNo);
  }
}

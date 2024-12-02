package com.edu.wing.card.dao;

import com.edu.wing.card.domain.CardVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class CardDaoImpl implements CardDao {
  @Autowired
  private SqlSession sqlSession;

  private final static String NAME_SPACE = "com.edu.wing.card.";

  @Override
  public List<CardVo> cardSelectList(Map<String, String> map) {
    return sqlSession.selectList(NAME_SPACE + "cardSelectList", map);
  }

  @Override
  public int cardSelectTotalCount(String categoryName) {
    return sqlSession.selectOne(NAME_SPACE + "cardSelectTotalCount", categoryName);
  }

  @Override
  public CardVo cardSelectOne(int cardNo) {
    return sqlSession.selectOne(NAME_SPACE + "cardSelectOne", cardNo);
  }

  @Override
  public CardVo getCardById(int cardNo) { return sqlSession.selectOne(NAME_SPACE + "getCardById", cardNo); }

  @Override
  public CardVo cardExist(String cardName) {
    return sqlSession.selectOne(NAME_SPACE + "cardExist", cardName);
  }

  @Override
  public void cardInsert(Map<String, String> map) {
    sqlSession.insert(NAME_SPACE + "cardInsertOne", map);
  }

  @Override
  public void markCardAsInactive(int cardNo) {
    sqlSession.delete(NAME_SPACE + "markCardAsInactive", cardNo);
  }

  @Override
  public void markCardAsActive(int cardNo) {
    sqlSession.delete(NAME_SPACE + "markCardAsActive", cardNo);
  }

  @Override
  public List<CardVo> userShowCardSelectList(Map<String, String> map) {
    return sqlSession.selectList(NAME_SPACE + "userShowCardSelectList", map);
  }

  @Override
  public int userShowCardSelectTotalCount(String categoryName) {
    return sqlSession.selectOne(NAME_SPACE + "userShowCardSelectTotalCount", categoryName);
  }

  @Override
  public CardVo getCardByName(String cardName) {
    return sqlSession.selectOne(NAME_SPACE + "cardExist", cardName);
  }

  @Override
  public Map<String, Object> userRecommendCardSelect(int memberNo) {
    return sqlSession.selectOne(NAME_SPACE + "userRecommendCardSelect", memberNo);
  }

}

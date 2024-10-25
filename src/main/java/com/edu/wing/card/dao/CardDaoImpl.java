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

  private final String namespace = "com.edu.wing.card.";

  @Override
  public List<CardVo> cardSelectList(Map<String, String> map) {
    return sqlSession.selectList(namespace + "cardSelectList", map);
  }

  @Override
  public int cardSelectTotalCount(String categoryName) {
    return sqlSession.selectOne(namespace + "cardSelectTotalCount", categoryName);
  }

  @Override
  public CardVo cardSelectOne(int cardNo) {
    return sqlSession.selectOne(namespace + "cardSelectOne", cardNo);
  }

  @Override
  public CardVo cardExist(String cardName) {
    return sqlSession.selectOne(namespace + "cardExist", cardName);
  }

  @Override
  public void cardInsert(Map<String, String> map) {
    sqlSession.insert(namespace + "cardInsertOne", map);
  }

  @Override
  public void cardDeleteOne(int cardNo) {
    sqlSession.delete(namespace + "cardDeleteOne", cardNo);
  }
}

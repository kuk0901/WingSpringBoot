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
  public List<SellingCardVo> sellingCardSelectList(int start, int end, int cardNo) {
    Map<String, Object> map = new HashMap<>();
    map.put("start", start);
    map.put("end", end);
    map.put("cardNo", cardNo);

    return sqlSession.selectList(namespace + "sellingCardSelectList", map);
  }

  @Override
  public int sellingCardSelectTotalCount(int cardNo) {
    return sqlSession.selectOne(namespace + "sellingCardSelectTotalCount", cardNo);
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
  public List<Map<String, Object>> sellingCardSelectOneForUserPage(int memberNo) {
    return sqlSession.selectList(namespace + "sellingCardSelectOneForUserPage", memberNo);
  }
}

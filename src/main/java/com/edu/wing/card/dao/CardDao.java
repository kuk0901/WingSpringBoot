package com.edu.wing.card.dao;

import com.edu.wing.card.domain.CardVo;

import java.util.List;
import java.util.Map;

public interface CardDao {
  List<CardVo> cardSelectList(Map<String, String> map);
  int cardSelectTotalCount(String categoryName);
  CardVo cardSelectOne(int cardNo);
  CardVo getCardById(int cardNo);
  CardVo cardExist(String cardName);
  void cardInsert(Map<String, String> map);
  void markCardAsInactive(int cardNo);
  void markCardAsActive(int cardNo);
  List<CardVo> userShowCardSelectList(Map<String, String> map);
  int userShowCardSelectTotalCount(String categoryName);
  CardVo getCardByName(String cardName);
  Map<String, Object> userRecommendCardSelect(int memberNo);
}

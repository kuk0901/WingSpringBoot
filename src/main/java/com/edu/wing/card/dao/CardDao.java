package com.edu.wing.card.dao;

import com.edu.wing.card.domain.CardVo;

import java.util.List;
import java.util.Map;

public interface CardDao {
  List<CardVo> cardSelectList(Map<String, String> map);
  int cardSelectTotalCount(String categoryName);
  CardVo cardSelectOne(int cardNo);
  CardVo cardExist(String cardName);
  void cardInsert(Map<String, String> map);
  void markCardAsDeleted(int cardNo);
  CardVo checkCardDeletedStatus(int cardNo);
  List<CardVo> userShowCardSelectList(Map<String, String> map);
  int userShowCardSelectTotalCount(String categoryName);
  CardVo getCardByName(String cardName);
}

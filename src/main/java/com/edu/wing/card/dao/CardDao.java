package com.edu.wing.card.dao;

import com.edu.wing.card.domain.CardVo;

import java.util.List;
import java.util.Map;

public interface CardDao {
  List<CardVo> cardSelectList(Map<String, String> map);
  int cardSelectTotalCount(String categoryName);
}

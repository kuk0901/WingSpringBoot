package com.edu.wing.card.service;

import com.edu.wing.card.domain.CardVo;

import java.util.List;

public interface CardService {
  List<CardVo> cardSelectList(int start, int end, String categoryName);
  int cardSelectTotalCount(String categoryName);
}

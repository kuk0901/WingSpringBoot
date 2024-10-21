package com.edu.wing.card.service;

import com.edu.wing.card.dao.CardDao;
import com.edu.wing.card.domain.CardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CardServiceImpl implements CardService {

  @Autowired
  private CardDao cardDao;

  @Override
  public List<CardVo> cardSelectList(int start, int end, String categoryName) {
    Map<String, String> map = new HashMap<>();
    map.put("start", String.valueOf(start));
    map.put("end", String.valueOf(end));
    map.put("categoryName", categoryName);

    return cardDao.cardSelectList(map);
  }

  @Override
  public int cardSelectTotalCount(String categoryName) {
    return cardDao.cardSelectTotalCount(categoryName);
  }
}

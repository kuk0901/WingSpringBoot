package com.edu.wing.sellingCard.service;

import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.sellingCard.domain.SellingCardVo;

import java.util.List;
import java.util.Map;

public interface SellingCardService {
  List<SellingCardVo> sellingCardSelectList(int start, int end, int cardNo);
  int sellingCardSelectTotalCount(int cardNo);
  Map<String, Object> sellingCardSelectOne(int sellingCardNo);
  int countActiveSellingCardsByCardNo(int cardNo);
  boolean memberSellingCardExist(SellingCardVo sellingCardVo);
  Map<String, Object> processMemberCardPurchase(SellingCardVo sellingCardVo, AccountBookVo accountBookVo);
}

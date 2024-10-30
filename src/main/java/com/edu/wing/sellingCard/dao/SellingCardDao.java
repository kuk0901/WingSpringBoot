package com.edu.wing.sellingCard.dao;

import com.edu.wing.sellingCard.domain.SellingCardVo;

import java.util.List;
import java.util.Map;

public interface SellingCardDao {
  List<SellingCardVo> sellingCardSelectList(int start, int end, int cardNo);
  int sellingCardSelectTotalCount(int cardNo);
  Map<String, Object> sellingCardSelectOne(int sellingCardNo);
  int countActiveSellingCardsByCardNo(int cardNo);
  List<SellingCardVo> memberSellingCardExist(SellingCardVo sellingCardVo);
  void memberPurchaseCard(SellingCardVo sellingCardVo);
  SellingCardVo memberPurchaseCardCheck(SellingCardVo sellingCardVo);
}

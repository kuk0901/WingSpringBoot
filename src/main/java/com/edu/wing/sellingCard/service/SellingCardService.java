package com.edu.wing.sellingCard.service;

import com.edu.wing.sellingCard.domain.SellingCardVo;

import java.util.List;
import java.util.Map;

public interface SellingCardService {
  List<SellingCardVo> sellingCardSelectList(int start, int end, int cardNo);
  int sellingCardSelectTotalCount(int cardNo);
  Map<String, Object> sellingCardSelectOne(int sellingCardNo);
}

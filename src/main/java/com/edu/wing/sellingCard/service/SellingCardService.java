package com.edu.wing.sellingCard.service;

import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.sellingCard.domain.SellingCardVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SellingCardService {
  List<SellingCardVo> sellingCardSelectList(int start, int end, int cardNo, String termination);
  int sellingCardSelectTotalCount(int cardNo, String termination);
  Map<String, Object> sellingCardSelectOne(int sellingCardNo);

  int countActiveSellingCardsByCardNo(int cardNo);
  boolean memberSellingCardExist(SellingCardVo sellingCardVo);
  Map<String, Object> processMemberCardPurchase(SellingCardVo sellingCardVo, AccountBookVo accountBookVo);

  List<Map<String, Object>> sellingCardSelectOneForUserPage(int memberNo);

  void deleteCardSoft(Map<String, Object> cardInfo, int memberNo);

  List<HashMap<String, Object>> totalCardsSoldLast5Years();
  List<HashMap<String, Object>> recommendedCardsPurchasedLast5Years();
  List<HashMap<String, Object>> terminatedCardsLast5Years();

  Map<String, Object> processMemberRecommendedCardPurchase(SellingCardVo sellingCardVo, AccountBookVo accountBookVo);
}

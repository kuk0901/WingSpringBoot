package com.edu.wing.sellingCard.dao;

import com.edu.wing.sellingCard.domain.SellingCardVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SellingCardDao {
  List<SellingCardVo> sellingCardSelectList(Map<String, Object> map);
  int sellingCardSelectTotalCount(Map<String, Object> map);
  Map<String, Object> sellingCardSelectOne(int sellingCardNo);

  int countActiveSellingCardsByCardNo(int cardNo);
  List<SellingCardVo> memberSellingCardExist(SellingCardVo sellingCardVo);
  void memberPurchaseCard(SellingCardVo sellingCardVo);
  SellingCardVo memberPurchaseCardCheck(SellingCardVo sellingCardVo);

  List<Map<String, Object>> sellingCardSelectOneForUserPage(int memberNo);

  void deleteCardSoft(Map<String, Object> cardInfo);
  SellingCardVo deleteCardSoftCheck(Map<String, Object> cardInfo);

  List<HashMap<String, Object>> totalCardsSoldLast5Years();
  List<HashMap<String, Object>> recommendedCardsPurchasedLast5Years();
  List<HashMap<String, Object>> terminatedCardsLast5Years();

  void memberPurchaseRecommendedCard(SellingCardVo sellingCardVo);

}

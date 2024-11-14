package com.edu.wing.card.service;

import com.edu.wing.card.dao.CardDao;
import com.edu.wing.card.domain.CardVo;
import com.edu.wing.cardBenefit.dao.CardBenefitDao;
import com.edu.wing.cardBenefit.domain.CardBenefitVo;
import com.edu.wing.util.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

@Service
public class CardServiceImpl implements CardService {
  @Autowired
  private CardDao cardDao;

  @Autowired
  private CardBenefitDao cardBenefitDao;

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

  @Override
  public CardVo cardSelectOne(int cardNo) {
    return cardDao.cardSelectOne(cardNo);
  }

  @Override
  public boolean cardExist(String cardName) {
    return cardDao.cardExist(cardName) != null;
  }

  @Override
  @Transactional
  public int insertCardAndBenefits(Map<String, String> formData, MultipartFile file, String benefitsJson) {
    try {
      // 파일 처리
      FileUtils fileUtils = new FileUtils();
      Map<String, String> cardImageInfo = fileUtils.parseInsertFileInfo(file);
      formData.put("originalFileName", cardImageInfo.get("originalFileName"));
      formData.put("storedFileName", cardImageInfo.get("storedFileName"));

      Type listType = new TypeToken<List<CardBenefitVo>>() {}.getType();
      List<CardBenefitVo> cardBenefitVoList = new Gson().fromJson(benefitsJson, listType);


      formData.remove("benefits"); // 기존 혜택 정보 제거

      cardDao.cardInsert(formData); // card insert

      CardVo cardVo = cardDao.cardExist(formData.get("cardName"));

      if (cardVo == null) {
        return 0;
      }

      int cardNo = cardVo.getCardNo(); // 카드 번호 가져오기

      // benefit insert
      for (CardBenefitVo benefit : cardBenefitVoList) {
        benefit.setCardNo(cardNo);
        cardBenefitDao.cardBenefitInsertOne(benefit);
      }

      return cardNo;
    } catch (IOException e) {
      throw new RuntimeException("파일 처리 중 오류가 발생했습니다. 파일 형식이나 크기를 확인하고 다시 시도해 주세요.");
    }
  }

  @Override
  public boolean softDeleteCardAndVerify(int cardNo) {
    cardDao.markCardAsDeleted(cardNo);

    CardVo cardVo = cardDao.checkCardDeletedStatus(cardNo);

    return cardVo != null;
  }

  @Override
  public List<CardVo> userShowCardSelectList(int start, int end, String categoryName) {
    Map<String, String> map = new HashMap<>();
    map.put("start", String.valueOf(start));
    map.put("end", String.valueOf(end));
    map.put("categoryName", categoryName);

    return cardDao.userShowCardSelectList(map);
  }

  @Override
  public int userShowCardSelectTotalCount(String categoryName) {
    return cardDao.userShowCardSelectTotalCount(categoryName);
  }

  @Override
  public CardVo getCardByName(String cardName) {
    return cardDao.getCardByName(cardName);
  }

  @Override
  public Map<String, Object> userRecommendCardSelect(int memberNo) {
    List<Map<String, Object>> resultList = cardDao.userRecommendCardSelect(memberNo);

    // Iterator를 사용하여 "No Recommendation" 항목 제거
    Iterator<Map<String, Object>> iterator = resultList.iterator();
    while (iterator.hasNext()) {
      Map<String, Object> item = iterator.next();
      if ("No Recommendation".equals(item.get("CARDNAME"))) {
        iterator.remove();
      }
    }

    // 결과가 비어있지 않은 경우에만 처리
    if (!resultList.isEmpty()) {
      // 랜덤으로 하나의 항목 선택
      Random random = new Random();
      Map<String, Object> result = resultList.get(random.nextInt(resultList.size()));

      int cardNo = ((BigDecimal) result.get("CARDNO")).intValue();
      List<CardBenefitVo> cardBenefitList = cardBenefitDao.cardBenefitSelectListOne(cardNo);
      result.put("cardBenefitList", cardBenefitList);
      return result;
    } else {
      // 모든 항목이 제거된 경우 빈 맵 반환 또는 다른 적절한 처리
      return new HashMap<>();
    }
  }
}

package com.edu.wing.card.service;

import com.edu.wing.card.dao.CardDao;
import com.edu.wing.card.domain.CardVo;
import com.edu.wing.cardBenefit.dao.CardBenefitDao;
import com.edu.wing.cardBenefit.domain.CardBenefitVo;
import com.edu.wing.util.CustomException;
import com.edu.wing.util.FileUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  @Transactional
  // 카드 비활성화
  public boolean deactivateCard(int cardNo) throws CustomException {
    CardVo cardToActivate = cardDao.getCardById(cardNo);

    if (cardToActivate == null) {
      throw new CustomException("카드를 찾을 수 없습니다.");
    }

    cardDao.markCardAsInactive(cardNo);

    return verifyCardStatus(cardNo, "true");
  }

  @Override
  @Transactional
  public boolean activateCard(int cardNo) throws CustomException {
    CardVo cardToActivate = cardDao.getCardById(cardNo);
    if (cardToActivate == null) {
      throw new CustomException("카드를 찾을 수 없습니다.");
    }

    CardVo existingCard = cardDao.cardExist(cardToActivate.getCardName());
    if (existingCard != null && existingCard.getCardNo() != cardNo) {
      throw new CustomException("동일한 이름의 카드가 이미 활성화되어 있습니다.");
    }

    cardDao.markCardAsActive(cardNo);
    return verifyCardStatus(cardNo, "false");
  }

  private boolean verifyCardStatus(int cardNo, String expectedStatus) {
    CardVo cardVo = cardDao.getCardById(cardNo);
    if (cardVo == null) {
      throw new CustomException("카드를 찾을 수 없습니다.");
    }

    String actualStatus = cardVo.getIsDeleted();
    if (actualStatus == null) {
      throw new IllegalStateException("카드 상태를 확인할 수 없습니다.");
    }
    return expectedStatus.equalsIgnoreCase(actualStatus);
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
    return cardDao.userRecommendCardSelect(memberNo);

  }
}

package com.edu.wing.sellingCard.service;

import com.edu.wing.accountbook.dao.AccountBookDao;
import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.member.dao.MemberDao;
import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.sellingCard.dao.SellingCardDao;
import com.edu.wing.sellingCard.domain.SellingCardVo;
import com.edu.wing.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

public class SellingCardServiceImpl implements SellingCardService {
  private final String STATUS = "status";
  private final String STATUS_SUCCESS = "success";
  private final String STATUS_FAIL = "failed";
  private final String STATUS_ERROR = "error";
  private final String ALERT_MSG = "alertMsg";

  @Autowired
  private SellingCardDao sellingCardDao;

  @Autowired
  private AccountBookDao accountBookDao;

  @Autowired
  private MemberDao memberDao;

  @Override
  public List<SellingCardVo> sellingCardSelectList(int start, int end, int cardNo, String termination) {
    Map<String, Object> map = new HashMap<>();
    map.put("start", start);
    map.put("end", end);
    map.put("cardNo", cardNo);
    map.put("termination", termination);

    return sellingCardDao.sellingCardSelectList(map);
  }

  @Override
  public int sellingCardSelectTotalCount(int cardNo, String termination) {
    HashMap<String, Object> map = new HashMap<>();
    map.put("cardNo", cardNo);
    map.put("termination", termination);

    return sellingCardDao.sellingCardSelectTotalCount(map);
  }
  @Override
  public Map<String, Object> sellingCardSelectOne(int sellingCardNo) {
    return sellingCardDao.sellingCardSelectOne(sellingCardNo);
  }

  @Override
  public int countActiveSellingCardsByCardNo(int cardNo) {
    return sellingCardDao.countActiveSellingCardsByCardNo(cardNo);
  }

  @Override
  public boolean memberSellingCardExist(SellingCardVo sellingCardVo) {
    List<SellingCardVo> results = sellingCardDao.memberSellingCardExist(sellingCardVo);

    return !results.isEmpty();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Map<String, Object> processMemberCardPurchase(SellingCardVo sellingCardVo, AccountBookVo accountBookVo) throws RuntimeException {
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put(STATUS, STATUS_FAIL);

    sellingCardDao.memberPurchaseCard(sellingCardVo);

    SellingCardVo checkSellingCardVo = sellingCardDao.memberPurchaseCardCheck(sellingCardVo);

    if (checkSellingCardVo == null) {
      throw new RuntimeException("카드 구매 내역이 확인되지 않았습니다. 잠시 후 다시 시도해 주세요.");
    }

    accountBookDao.cardPurchaseOfAccountBook(accountBookVo);

    AccountBookVo checkAccountBookVo = accountBookDao.verifyTodayCardPurchaseAccountBookEntry(accountBookVo);

    if (checkAccountBookVo == null) {
      throw new RuntimeException("가계부에 카드 구매 내역이 확인되지 않았습니다. 관리자에게 문의해 주세요.");
    }

    memberDao.updateMemberCardPurchase(sellingCardVo.getMemberNo());
    MemberVo memberVo = memberDao.checkMemberProductPurchase(sellingCardVo.getMemberNo());

    if ("N".equals(memberVo.getProductPurchase())) {
      throw new RuntimeException("회원 정보에 카드 구매 내역이 확인되지 않았습니다. 관리자에게 문의해 주세요.");
    }

    resultMap.put(STATUS, STATUS_SUCCESS);
    resultMap.put(ALERT_MSG, "WING_ 카드를 신청해 주셔서 감사합니다!");

    return resultMap;
  }

  @Override
  public Map<String, Object> sellingCardSelectOneForUserPage(int memberNo) {
    return sellingCardDao.sellingCardSelectOneForUserPage(memberNo);
  }

  @Override
  @Transactional
  public void deleteCardSoft(Map<String, Object> cardInfo, int memberNo) throws CustomException {
    // 1. 카드 업데이트
    sellingCardDao.deleteCardSoft(cardInfo);

    // 2. 카드 조회
    SellingCardVo sellingCardVo = sellingCardDao.deleteCardSoftCheck(cardInfo);

    if (sellingCardVo.getMemberNo() != 1 && "N".equals(sellingCardVo.getCardTermination())) {
      throw new CustomException("카드 해지 처리에 실패했습니다.");
    }

    // 3. 회원 PRODUCT_PURCHASE 'N'으로 수정
    memberDao.updateMemberProductPurchase(memberNo);

    // 4. 회원 정보 조회
    MemberVo memberVo = memberDao.checkMemberProductPurchase(memberNo);

    if ("Y".equals(memberVo.getProductPurchase())) {
      throw new CustomException("회원 상태 업데이트에 실패했습니다.");
    }
  }

  @Override
  public List<HashMap<String, Object>> totalCardsSoldLast5Years() {
    return sellingCardDao.totalCardsSoldLast5Years();
  }

  @Override
  public List<HashMap<String, Object>> recommendedCardsPurchasedLast5Years() {
    return sellingCardDao.recommendedCardsPurchasedLast5Years();
  }

  @Override
  public List<HashMap<String, Object>> terminatedCardsLast5Years() {
    return sellingCardDao.terminatedCardsLast5Years();
  }

  @Override
  public Map<String, Object> processMemberRecommendedCardPurchase(SellingCardVo sellingCardVo, AccountBookVo accountBookVo) {
    Map<String, Object> resultMap = new HashMap<>();

    try {
      sellingCardDao.memberPurchaseRecommendedCard(sellingCardVo);
      resultMap.put(STATUS, STATUS_SUCCESS);

      SellingCardVo checkSellingCardVo = sellingCardDao.memberPurchaseCardCheck(sellingCardVo);

      if (checkSellingCardVo == null) {
        resultMap.put(STATUS, STATUS_FAIL);
        throw new RuntimeException("카드 구매 내역이 확인되지 않았습니다. 잠시 후 다시 시도해 주세요.");
      }

      accountBookDao.cardPurchaseOfAccountBook(accountBookVo);

      AccountBookVo checkAccountBookVo = accountBookDao.verifyTodayCardPurchaseAccountBookEntry(accountBookVo);

      if (checkAccountBookVo == null) {
        resultMap.put(STATUS, STATUS_FAIL);
        throw new RuntimeException("가계부에 카드 구매 내역이 확인되지 않았습니다. 관리자에게 문의해 주세요.");
      }

      resultMap.put(STATUS, STATUS_SUCCESS);
      resultMap.put(ALERT_MSG, "WING_ 카드를 신청해 주셔서 감사합니다!");
    }
    catch (Exception e) {
      resultMap.put(STATUS, STATUS_ERROR);
      resultMap.put(ALERT_MSG, e.getMessage());
    }

    return resultMap;
  }
}
package com.edu.wing.accountbook.service;

import com.edu.wing.accountbook.dao.AccountBookDao;
import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.sellingCard.dao.SellingCardDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountBookServiceImpl implements AccountBookService {

  @Autowired
  private AccountBookDao accountBookDao;
    @Autowired
    private SellingCardDao sellingCardDao;

  @Override
  public List<AccountBookVo> selectAccountBook() {
    return accountBookDao.selectAccountBook();
  }

  @Override
  public List<AccountBookVo> getAccountBooks(Map<String, Object> params) {
    return accountBookDao.selectAccountBooks(params);
  }
  @Override
  public List<String> getPlusCategoryList() {
    return accountBookDao.selectPlusCategories();
  }
  @Override
  public List<String> getMinusCategoryList() {
    return accountBookDao.selectMinusCategories();
  }
  @Override
  public List<String> getPaymentMethodList() {
    return accountBookDao.selectPaymentMethods();
  }

  @Override
  public List<AccountBookVo> getTopPaymentMethods() {
    return accountBookDao.getTopPaymentMethods();
  }

  @Override
  public List<AccountBookVo> getTopCategories() {
    return accountBookDao.getTopCategories();
  }

  

  @Override
  public List<AccountBookVo> getAccountBooksByRecentDate(int memberNo, int limit) {
    Map<String, Object> params = new HashMap<>();
    params.put("memberNo", memberNo);
    params.put("limit", limit);
    return accountBookDao.selectAccountBookByRecentDate(params);
  }

  @Override
  public int getMonthlyEntryCount(int memberNo, LocalDate startDate, LocalDate endDate) {
    Map<String, Object> params = new HashMap<>();
    params.put("memberNo", memberNo);
    params.put("startDate", startDate);
    params.put("endDate", endDate);
    return accountBookDao.selectMonthlyEntryCount(params);
  }

  @Override
  public List<AccountBookVo> getMonthlyEntries(int memberNo, LocalDate startDate, LocalDate endDate) {
    return accountBookDao.getMonthlyEntries(memberNo, startDate, endDate);
  }

  @Override
  public int addAccountBook(Map<String, Object> params) {
    int result = accountBookDao.insertAccountBook(params);
    if (result > 0) {
      // accountBookNo와 memberNo를 추출하여 PAYBACK 삽입
      Map<String, Object> sellingCardCheckParams  = new HashMap<>();
      sellingCardCheckParams .put("memberNo", params.get("memberNo"));

      // SELLING_CARD가 존재할 때만 PAYBACK을 삽입하도록 조건 추가
      boolean hasSellingCard = sellingCardDao.checkSellingCardExists(sellingCardCheckParams);
      // 기본값 설정
      int minusCategoryNo =1;
      int plusCategoryNo = 1; // 기본값
      int paymentMethodNo = 1; // 기본값

      // plusCategoryNo가 String 타입인 경우 int로 변환
      if (params.get("plusCategoryNo") != null && params.get("plusCategoryNo") instanceof String) {
        plusCategoryNo = Integer.parseInt((String) params.get("plusCategoryNo"));
      }

      // paymentMethodNo가 String 타입인 경우 int로 변환
      if (params.get("paymentMethodNo") != null && params.get("paymentMethodNo") instanceof String) {
        paymentMethodNo = Integer.parseInt((String) params.get("paymentMethodNo"));
      }

      if (hasSellingCard  && plusCategoryNo == 1 && paymentMethodNo== 2) {
        Map<String, Object> paybackData = new HashMap<>();
        paybackData.put("accountBookNo", params.get("accountBookNo"));
        paybackData.put("memberNo", params.get("memberNo"));

        // PAYBACK 삽입
        return accountBookDao.insertPayback(paybackData);
      }
    }
    return result;
  }

  @Override
  public List<AccountBookVo> getAccountBooksByMonth(int memberNo, LocalDate startDate, LocalDate endDate, int limit) {
    // DAO에서 데이터를 가져옴
    List<AccountBookVo> accountBooks = accountBookDao.selectAccountBookByMonth(
            java.sql.Date.valueOf(startDate),
            java.sql.Date.valueOf(endDate),
            memberNo,
            limit
    );
    // 날짜 형식을 yyyy-MM-dd로 변환하여 formattedCreDate에 설정
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    accountBooks.forEach(book -> {
      if (book.getCreDate() != null) {
        book.setFormattedCreDate(dateFormat.format(book.getCreDate()));
      }
    });

    return accountBooks;
  }

  @Override
  public AccountBookVo getAccountBookDetail(int accountBookNo, int memberNo) {
    return accountBookDao.selectAccountBookDetail(accountBookNo, memberNo);
  }

  @Override
  public void deleteAccountBook(int accountBookNo) {
    accountBookDao.deleteAccountBook(accountBookNo);
  }

  @Override
  public int updateAccountBook(Map<String, Object> params) {
    return accountBookDao.updateAccountBook(params);
  }

  @Override
  public void deleteAllAccountBook(int memberNo) {
    accountBookDao.deleteAllAccountBook(memberNo);
  }

  @Override
  public List<AccountBookVo> selectMonthlyExpenseBook(int memberNo, Date startDate, Date endDate) {
    List<AccountBookVo> accountBooks = accountBookDao.selectMonthlyExpenseBook(
            memberNo,startDate,endDate
    );
    // 날짜 형식을 yyyy-MM-dd로 변환하여 formattedCreDate에 설정
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    accountBooks.forEach(book -> {
      if (book.getCreDate() != null) {
        book.setFormattedCreDate(dateFormat.format(book.getCreDate()));
      }
    });

    return accountBooks;
  }

  @Override
  public List<AccountBookVo> selectMonthlyIncomeBook(int memberNo, Date startDate, Date endDate) {
    List<AccountBookVo> accountBooks = accountBookDao.selectMonthlyIncomeBook(
            memberNo,startDate,endDate
    );
    // 날짜 형식을 yyyy-MM-dd로 변환하여 formattedCreDate에 설정
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    accountBooks.forEach(book -> {
      if (book.getCreDate() != null) {
        book.setFormattedCreDate(dateFormat.format(book.getCreDate()));
      }
    });

    return accountBooks;
  }

  @Override
  public int insertPayback(Map<String, Object> paybackData) {
    return accountBookDao.insertPayback(paybackData);
  }

  @Override
  public List<AccountBookVo> getMonthlyPayback(Map<String, Object> params) {
    return accountBookDao.getMonthlyPayback(params);
  }

  @Override
  public List<AccountBookVo> getCardDetailForMypage(int memberNo, Integer categoryNo, LocalDate startDate) {
    return accountBookDao.getCardDetailForMypage(memberNo, categoryNo, startDate);
  }

  @Override
  public int softDeleteAccountBook(int accountBookNo) {
    return accountBookDao.softDeleteAccountBook(accountBookNo);
  }

  @Override
  public int softAllDeleteAccountBook(int accountBookNo) {
    return accountBookDao.softAllDeleteAccountBook(accountBookNo);
  }

  @Override
  public int deleteAllPayBack(int memberNo) {
    return accountBookDao.deleteAllPayBack(memberNo);
  }


}




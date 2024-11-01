package com.edu.wing.accountbook.service;

import com.edu.wing.accountbook.dao.AccountBookDao;
import com.edu.wing.accountbook.domain.AccountBookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountBookServiceImpl implements AccountBookService {

  @Autowired
  private AccountBookDao accountBookDao;

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
  public int getMonthlyEntryCount(int memberNo, String startDate, String endDate) {
    Map<String, Object> params = new HashMap<>();
    params.put("memberNo", memberNo);
    params.put("startDate", startDate);
    params.put("endDate", endDate);
    return accountBookDao.selectMonthlyEntryCount(params);
  }

  @Override
  public List<AccountBookVo> getMonthlyEntries(int memberNo, String startDate, String endDate) {
    return accountBookDao.getMonthlyEntries(memberNo, startDate, endDate);
  }

  @Override
  public int addAccountBook(Map<String, Object> params) {
    return accountBookDao.insertAccountBook(params);
  }

  @Override
  public List<AccountBookVo> getAccountBooksByMonth(int memberNo, LocalDate startDate, LocalDate endDate, int limit) {
    return accountBookDao.selectAccountBookByMonth(
        java.sql.Date.valueOf(startDate),
        java.sql.Date.valueOf(endDate),
        memberNo,
        limit);
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

}
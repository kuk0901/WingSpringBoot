package com.edu.wing.accountbook.service;

import com.edu.wing.accountbook.domain.AccountBookVo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AccountBookService {

  List<AccountBookVo> selectAccountBook();
  List<AccountBookVo> getAccountBooks(Map<String, Object> params);
  List<String> getPlusCategoryList();
  List<String> getMinusCategoryList();// 카테고리 조회 메소드
  List<String> getPaymentMethodList(); // 결제 방법 조회 메소드
  List<AccountBookVo> getTopPaymentMethods(); // 상위 3개 결제 방법 조회
  List<AccountBookVo> getTopCategories();
  void accountBookDelete(int memberNo); // 회원 번호로 가계부 삭제 메서드

  List<AccountBookVo> getAccountBooksByRecentDate(int memberNo, int limit);//유저용 초기화면용 list
  int getMonthlyEntryCount(int memberNo, String startDate, String endDate);
  // 월별 가계부 내역 가져오기
  List<AccountBookVo> getMonthlyEntries(int memberNo, String startDate, String endDate);
  int addAccountBook(Map<String, Object> params); // 가계부 추가 메서드

  List<AccountBookVo> getAccountBooksByMonth(int memberNo, LocalDate startDate, LocalDate endDate, int limit);

  AccountBookVo getAccountBookDetail(int accountBookNo, int memberNo); //디테일
}
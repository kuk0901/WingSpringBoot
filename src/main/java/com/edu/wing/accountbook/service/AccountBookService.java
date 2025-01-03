package com.edu.wing.accountbook.service;

import com.edu.wing.accountbook.domain.AccountBookVo;

import java.time.LocalDate;

import java.util.Date;
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


    List<AccountBookVo> getAccountBooksByRecentDate(int memberNo, int limit);//유저용 초기화면용 list
    int getMonthlyEntryCount(int memberNo, LocalDate startDate, LocalDate endDate);
    // 월별 가계부 내역 가져오기
    List<AccountBookVo> getMonthlyEntries(int memberNo, LocalDate startDate, LocalDate endDate);
    int addAccountBook(Map<String, Object> params); // 가계부 추가 메서드

    List<AccountBookVo> getAccountBooksByMonth(int memberNo, LocalDate startDate, LocalDate endDate, int limit);

    AccountBookVo getAccountBookDetail(int accountBookNo, int memberNo); //디테일
    void deleteAccountBook(int accountBookNo); //가계부삭제
    int updateAccountBook(Map<String, Object> params);//업데이트
    void deleteAllAccountBook(int memberNo);
    List<AccountBookVo> selectMonthlyExpenseBook(int memberNo, Date startDate, Date endDate);
    List<AccountBookVo> selectMonthlyIncomeBook(int memberNo, Date startDate, Date endDate);


    int insertPayback(Map<String, Object> paybackData);

    List<AccountBookVo> getMonthlyPayback(Map<String, Object> params);
    List<AccountBookVo> getCardDetailForMypage(int memberNo, Integer categoryNo, LocalDate startDate);
    int softDeleteAccountBook(int accountBookNo);
    int softAllDeleteAccountBook(int accountBookNo);
    int deleteAllPayBack(int memberNo);
}


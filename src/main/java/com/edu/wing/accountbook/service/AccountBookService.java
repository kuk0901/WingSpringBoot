package com.edu.wing.accountbook.service;

import com.edu.wing.accountbook.domain.AccountBookVo;

import java.util.List;
import java.util.Map;

public interface AccountBookService {

    List<AccountBookVo> selectAccountBook();
    List<AccountBookVo> getAccountBooks(Map<String, Object> params);
    List<String> getCategoryList(); // 카테고리 조회 메소드
    List<String> getPaymentMethodList(); // 결제 방법 조회 메소드
    List<AccountBookVo> getTopPaymentMethods(); // 상위 3개 결제 방법 조회
    List<AccountBookVo> getTopCategories();
}

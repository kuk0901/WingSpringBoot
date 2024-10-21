package com.edu.wing.accountbook.dao;

import com.edu.wing.accountbook.domain.AccountBookVo;

import java.util.List;
import java.util.Map;

public interface AccountBookDao {



    List<AccountBookVo> selectAccountBook();
    List<AccountBookVo> selectAccountBooks(Map<String, Object> params);
    List<String> selectCategories();
    List<String> selectPaymentMethods();
}

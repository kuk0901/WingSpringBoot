package com.edu.wing.accountbook.service;

import com.edu.wing.accountbook.dao.AccountBookDao;
import com.edu.wing.accountbook.domain.AccountBookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<String> getCategoryList() {
        return accountBookDao.selectCategories();
    }

    public List<String> getPaymentMethodList() {
        return accountBookDao.selectPaymentMethods();
    }
}
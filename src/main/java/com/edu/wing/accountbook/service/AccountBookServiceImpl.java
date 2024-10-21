package com.edu.wing.accountbook.service;

import com.edu.wing.accountbook.dao.AccountBookDao;
import com.edu.wing.accountbook.domain.AccountBookVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountBookServiceImpl implements AccountBookService {

    @Autowired
    private AccountBookDao accountBookDao;

    @Override
    public List<AccountBookVo> selectAccountBook() {
        return accountBookDao.selectAccountBook();
    }
}
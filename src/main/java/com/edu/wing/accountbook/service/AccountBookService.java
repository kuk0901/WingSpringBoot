package com.edu.wing.accountbook.service;

import com.edu.wing.accountbook.domain.AccountBookVo;

import java.util.List;

public interface AccountBookService {
    List<AccountBookVo> selectAccountBook();
}

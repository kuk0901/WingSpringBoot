package com.edu.wing.accountbook.dao;

import com.edu.wing.accountbook.domain.AccountBookVo;

import java.util.List;

public interface AccountBookDao {

    List<AccountBookVo> selectAccountBook();
}

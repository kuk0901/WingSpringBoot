package com.edu.wing.accountbook.dao;

import com.edu.wing.accountbook.domain.AccountBookVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountBookDaoImpl implements AccountBookDao {

    @Autowired
    private SqlSession sqlSession;

    private static final String NAMESPACE = "com.edu.wing.accountbook.";

    @Override
    public List<AccountBookVo> selectAccountBook() {
        return sqlSession.selectList(NAMESPACE + "selectAccountBook");
    }
}

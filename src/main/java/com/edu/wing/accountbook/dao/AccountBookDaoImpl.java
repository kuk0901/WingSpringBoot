package com.edu.wing.accountbook.dao;

import com.edu.wing.accountbook.domain.AccountBookVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AccountBookDaoImpl implements AccountBookDao {

    @Autowired
    private SqlSession sqlSession;

    private static final String NAMESPACE = "com.edu.wing.accountbook.";


    @Override
    public List<AccountBookVo> selectAccountBook() {
        return sqlSession.selectList(NAMESPACE+"selectAccountBook");
    }

    @Override
    public List<AccountBookVo> selectAccountBooks(Map<String, Object> params) {
        return sqlSession.selectList(NAMESPACE+"selectAccountBooks", params);
    }

    @Override
    public List<String> selectCategories() {
        return sqlSession.selectList(NAMESPACE+"selectCategories");
    }

    @Override
    public List<String> selectPaymentMethods() {
        return sqlSession.selectList(NAMESPACE+"selectPaymentMethods");
    }

    @Override
    public List<AccountBookVo> getTopPaymentMethods() {
        return sqlSession.selectList(NAMESPACE+"getTopPaymentMethods");
    }

    @Override
    public List<AccountBookVo> getTopCategories() {
        return sqlSession.selectList(NAMESPACE + "getTopCategories");
    }

    @Override
    public void accountBookDelete(int memberNo) {
        sqlSession.delete(NAMESPACE + "accountBookDelete", memberNo);
    }

}

package com.edu.wing.minusCategory.dao;


import com.edu.wing.minusCategory.domain.MinusCategoryVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MinusCategoryDaoImpl implements MinusCategoryDao {

    @Autowired
    private SqlSession sqlSession;

    String namespace = "com.edu.wing.minusCategory.";

    @Override
    public List<MinusCategoryVo> minusCategorySelectList() {
        return sqlSession.selectList(namespace + "minusCategorySelectList");
    }
}

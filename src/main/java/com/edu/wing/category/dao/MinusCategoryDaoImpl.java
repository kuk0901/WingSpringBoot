package com.edu.wing.category.dao;


import com.edu.wing.category.domain.CategoryVo;
import com.edu.wing.category.domain.MinusCategoryVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MinusCategoryDaoImpl implements CategoryDao {

  @Autowired
  private SqlSession sqlSession;

  String namespace = "com.edu.wing.category.";

//    public List<CategoryVo> minusCategorySelectList(){
//
//
//        return null;
//    }

  @Override
  public List<CategoryVo> categorySelectList() {
    return sqlSession.selectList(namespace + "categorySelectList");
  }

  @Override
  public int categoryInsertOne(CategoryVo categoryVo) {
    // TODO Auto-generated method stub

    return sqlSession.insert(namespace + "categoryInsertOne", categoryVo);

  }

  @Override
  public MinusCategoryVo minusCategorySelectOne(int no) {
    // TODO Auto-generated method stub
    return sqlSession.selectOne(namespace + "minusCategorySelectOne", no);
  }

  @Override
  public int minusCategoryUpdateOne(MinusCategoryVo minusCategoryVo) {
    // TODO Auto-generated method stub
    return sqlSession.update(namespace + "minusCategoryUpdateOne", minusCategoryVo);
  }

  @Override
  public int minusCategoryDeleteOne(int no) {
    // TODO Auto-generated method stub
    return sqlSession.delete(namespace + "minusCategoryDeleteOne", no);
  }
}

package com.edu.wing.category.dao;

import com.edu.wing.category.domain.CategoryVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Qualifier("plusCategoryDao")
public class PlusCategoryDaoImpl implements CategoryDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.category.";

  @Override
  public List<CategoryVo> categorySelectList() {
    return sqlSession.selectList(NAMESPACE + "plusCategorySelectList");
  }

  @Override
  public int categoryInsertOne(CategoryVo categoryVo) {
    return sqlSession.insert(NAMESPACE + "plusCategoryInsertOne", categoryVo);
  }

  @Override
  public CategoryVo categorySelectOne(int no) {
    return sqlSession.selectOne(NAMESPACE + "plusCategorySelectOne", no);
  }

  @Override
  public int categoryUpdateOne(CategoryVo categoryVo) {
    return sqlSession.update(NAMESPACE + "plusCategoryUpdateOne", categoryVo);
  }

  @Override
  public int categoryDeleteOne(int no) {
    return sqlSession.delete(NAMESPACE + "plusCategoryDeleteOne", no);
  }
}
package com.edu.wing.category.dao;

import com.edu.wing.category.domain.PlusCategoryVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class PlusCategoryDaoImpl implements PlusCategoryDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.category.";

  @Override
  public List<PlusCategoryVo> plusCategorySelectList() {
    return sqlSession.selectList(NAMESPACE + "plusCategorySelectList");
  }

  @Override
  public List<String> getPlusCategoryNames(){
    return sqlSession.selectList(NAMESPACE + "getPlusCategoryNames");
  }

  @Override
  public PlusCategoryVo plusCategoryExists(String categoryName) {
    return sqlSession.selectOne(NAMESPACE + "plusCategoryExists", categoryName);
  }

  @Override
  public int selectPlusCategoryNoByName(String categoryName) {
    return sqlSession.selectOne(NAMESPACE + "selectPlusCategoryNoByName", categoryName);
  }

  @Override
  public void plusCategoryInsertOne(String categoryName) {
    sqlSession.insert(NAMESPACE + "plusCategoryInsertOne", categoryName);
  }

  @Override
  public PlusCategoryVo plusCategoryUpdateOne(PlusCategoryVo plusCategoryVo) {
    sqlSession.update(NAMESPACE + "plusCategoryUpdateOne", plusCategoryVo);

    return plusCategoryVo;
  }

  @Override
  public PlusCategoryVo plusCategorySelectOne(int categoryNo) {
    return sqlSession.selectOne(NAMESPACE + "plusCategorySelectOne", categoryNo);
  }

  @Override
  public int plusCategoryTotalCount(int categoryNo) {
    return sqlSession.selectOne(NAMESPACE + "plusCategoryTotalCount", categoryNo);
  }

  @Override
  public boolean plusCategoryDeleteOne(int categoryNo) {
    int result = sqlSession.delete(NAMESPACE + "plusCategoryDeleteOne", categoryNo);

    return result > 0;
  }
}
package com.edu.wing.category.dao;

import com.edu.wing.category.domain.MinusCategoryVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class MinusCategoryDaoImpl implements MinusCategoryDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.category.";

  @Override
  public List<MinusCategoryVo> minusCategorySelectList() {
    return sqlSession.selectList(NAMESPACE + "minusCategorySelectList");
  }

  @Override
  public Map<String, Object> allCategorySelectList() {
    List<Map<String, Object>> categories = sqlSession.selectList(NAMESPACE + "allCategorySelectList");
    List<Map<String, Object>> minusCategories = categories.stream()
        .filter(cat -> "MINUS".equals(cat.get("category")))
        .collect(Collectors.toList());
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("minusCategoryList", minusCategories);
    return resultMap;
  }

  @Override
  public MinusCategoryVo minusCategoryExists(String categoryName) {
    return sqlSession.selectOne(NAMESPACE + "minusCategoryExists", categoryName);
  }

  @Override
  public int selectMinusCategoryNoByName(String categoryName) {
    return sqlSession.selectOne(NAMESPACE + "selectMinusCategoryNoByName", categoryName);
  }

  @Override
  public void minusCategoryInsertOne(String categoryName) {
    sqlSession.insert(NAMESPACE + "minusCategoryInsertOne", categoryName);
  }

  @Override
  public MinusCategoryVo minusCategoryUpdateOne(MinusCategoryVo minusCategoryVo) {
    sqlSession.update(NAMESPACE + "minusCategoryUpdateOne", minusCategoryVo);

    return minusCategoryVo;
  }

  @Override
  public MinusCategoryVo minusCategorySelectOne(int categooryNo) {
    return sqlSession.selectOne(NAMESPACE + "minusCategorySelectOne", categooryNo);
  }

  @Override
  public int minusCategoryTotalCount(int categoryNo){
    return sqlSession.selectOne(NAMESPACE + "minusCategoryTotalCount", categoryNo);
  }

  @Override
  public boolean minusCategoryDeleteOne(int categoryNo) {
    int result = sqlSession.delete(NAMESPACE + "minusCategoryDeleteOne", categoryNo);

    return result > 0;
  }
}
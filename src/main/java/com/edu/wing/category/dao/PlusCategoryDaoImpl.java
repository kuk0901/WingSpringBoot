package com.edu.wing.category.dao;

import com.edu.wing.category.domain.CategoryVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Repository
@Qualifier("plusCategoryDao")
public class PlusCategoryDaoImpl implements CategoryDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.category.dao.CategoryDao.";

  @Override
  public List<CategoryVo> categorySelectList() {
    return sqlSession.selectList(NAMESPACE + "plusCategorySelectList");
  }

//  @Override
//  public Map<String, Object> allCategorySelectList() {
//    Map<String, Object> resultMap = new HashMap<>();
//    List<CategoryVo> plusCategoryList = sqlSession.selectList(NAMESPACE + "plusCategorySelectList");
//    resultMap.put("plusCategoryList", plusCategoryList);
//    return resultMap;
//  }

  @Override
  public Map<String, Object> allCategorySelectList() {
    List<Map<String, Object>> categories = sqlSession.selectList(NAMESPACE + "allCategorySelectList");
    List<Map<String, Object>> plusCategories = categories.stream()
            .filter(cat -> "PLUS".equals(cat.get("category")))
            .collect(Collectors.toList());
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("plusCategoryList", plusCategories);
    return resultMap;
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
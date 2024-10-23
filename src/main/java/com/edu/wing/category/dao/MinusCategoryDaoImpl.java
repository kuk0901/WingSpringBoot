package com.edu.wing.category.dao;

import com.edu.wing.category.domain.CategoryVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Qualifier("minusCategoryDao")
public class MinusCategoryDaoImpl implements CategoryDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.category.dao.CategoryDao.";

  @Override
  public List<CategoryVo> categorySelectList() {
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
  public boolean categoryExists(Map<String, String> categoryMap) {
    Integer count = sqlSession.selectOne(NAMESPACE + "categoryExists", categoryMap);

    return count != null && count > 0;
  }

  @Override
  public boolean categoryInsertOne(Map<String, String> categoryMap) {
    Integer count = sqlSession.selectOne(NAMESPACE + "categoryExists", categoryMap);

    return count != null && count > 0;
  }

  @Override
  public CategoryVo categorySelectOne(int no) {
    return sqlSession.selectOne(NAMESPACE + "minusCategorySelectOne", no);
  }

  @Override
  public int categoryUpdateOne(CategoryVo categoryVo) {
    return sqlSession.update(NAMESPACE + "minusCategoryUpdateOne", categoryVo);
  }

  @Override
  public int categoryDeleteOne(int no) {
    return sqlSession.delete(NAMESPACE + "minusCategoryDeleteOne", no);
  }
}
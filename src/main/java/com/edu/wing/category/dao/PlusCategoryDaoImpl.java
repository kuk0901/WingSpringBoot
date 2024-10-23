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
@Qualifier("plusCategoryDao")
public class PlusCategoryDaoImpl implements CategoryDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.category.dao.CategoryDao.";

  @Override
  public List<CategoryVo> categorySelectList() {
    return sqlSession.selectList(NAMESPACE + "plusCategorySelectList");
  }

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
  public boolean categoryExists(Map<String, String> categoryMap) {
    Integer count = sqlSession.selectOne(NAMESPACE + "categoryExists", categoryMap);

    return count != null && count > 0;
  }

  @Override
  public boolean categoryInsertOne(Map<String, String> categoryMap) {
    int result = sqlSession.insert(NAMESPACE + "insertCategory", categoryMap);

    return result > 0;
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
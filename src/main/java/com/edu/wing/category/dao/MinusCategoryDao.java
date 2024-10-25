package com.edu.wing.category.dao;

import com.edu.wing.category.domain.MinusCategoryVo;

import java.util.List;
import java.util.Map;

public interface MinusCategoryDao {

  List<MinusCategoryVo> minusCategorySelectList();

  Map<String, Object> allCategorySelectList();

  MinusCategoryVo minusCategoryExists(String categoryName);

  int selectMinusCategoryNoByName(String categoryName);

  void minusCategoryInsertOne(String categoryName);

  MinusCategoryVo minusCategoryUpdateOne(MinusCategoryVo minusCategoryVo);

  MinusCategoryVo minusCategorySelectOne(int categoryNo);

  int minusCategoryTotalCount(int categoryNo);

  boolean minusCategoryDeleteOne(int categoryNo);
}
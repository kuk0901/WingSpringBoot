package com.edu.wing.category.service;

import com.edu.wing.category.domain.MinusCategoryVo;

import java.util.List;
import java.util.Map;

public interface MinusCategoryService {

  List<MinusCategoryVo> minusCategorySelectList();

  Map<String, Object> allCategorySelectList();

  boolean minusCategoryExists(String categoryName);

  boolean minusCategoryInsertOne(String categoryName);

  MinusCategoryVo minusCategoryUpdateOne(MinusCategoryVo minusCategoryVo);

  MinusCategoryVo minusCategorySelectOne(int categoryNo);

  int minusCategoryTotalCount(int categoryNo);

  boolean minusCategoryDeleteOne(int categoryNo);
}
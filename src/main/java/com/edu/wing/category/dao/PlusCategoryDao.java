package com.edu.wing.category.dao;

import com.edu.wing.category.domain.PlusCategoryVo;

import java.util.List;
import java.util.Map;

public interface PlusCategoryDao {

  List<PlusCategoryVo> plusCategorySelectList();

  Map<String, Object> allCategorySelectList();

  PlusCategoryVo plusCategoryExists(String categoryName);

  int selectPlusCategoryNoByName(String categoryName);

  void plusCategoryInsertOne(String categoryName);

  PlusCategoryVo plusCategoryUpdateOne(PlusCategoryVo plusCategoryVo);

  PlusCategoryVo plusCategorySelectOne(int categoryNo);

  int plusCategoryTotalCount(int categoryNo);

  boolean plusCategoryDeleteOne(int categoryNo);
}


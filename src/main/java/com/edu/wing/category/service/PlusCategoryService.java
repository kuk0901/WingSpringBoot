package com.edu.wing.category.service;

import com.edu.wing.category.domain.PlusCategoryVo;

import java.util.List;
import java.util.Map;

public interface PlusCategoryService {

  List<PlusCategoryVo> plusCategorySelectList();

  List<String> getPlusCategoryNames();

  boolean plusCategoryExists(String categoryName);

  boolean plusCategoryInsertOne(String categoryName);

  PlusCategoryVo plusCategoryUpdateOne(PlusCategoryVo plusCategoryVo);

  PlusCategoryVo plusCategorySelectOne(int categoryNo);

  int plusCategoryTotalCount(int categoryNo);

  boolean plusCategoryDeleteOne(int categoryNo);
}
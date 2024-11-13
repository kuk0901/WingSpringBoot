package com.edu.wing.category.service;

import com.edu.wing.category.domain.MinusCategoryVo;

import java.util.List;

public interface MinusCategoryService {

  List<MinusCategoryVo> minusCategorySelectList();

  List<String> getMinusCategoryNames();

  boolean minusCategoryExists(String categoryName);

  boolean minusCategoryInsertOne(String categoryName);

  boolean minusCategoryUpdateOne(MinusCategoryVo minusCategoryVo);

  MinusCategoryVo minusCategorySelectOne(int categoryNo);

  int minusCategoryTotalCount(int categoryNo);

  boolean minusCategoryDeleteOne(int categoryNo);
}
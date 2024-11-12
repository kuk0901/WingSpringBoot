package com.edu.wing.category.dao;

import com.edu.wing.category.domain.MinusCategoryVo;

import java.util.List;

public interface MinusCategoryDao {

  List<MinusCategoryVo> minusCategorySelectList();

  List<String> getMinusCategoryNames();

  MinusCategoryVo minusCategoryExists(String categoryName);

  int selectMinusCategoryNoByName(String categoryName);

  void minusCategoryInsertOne(String categoryName);

  int minusCategoryUpdateOne(MinusCategoryVo minusCategoryVo);

  MinusCategoryVo minusCategorySelectOne(int categoryNo);

  int minusCategoryTotalCount(int categoryNo);

  boolean minusCategoryDeleteOne(int categoryNo);
}
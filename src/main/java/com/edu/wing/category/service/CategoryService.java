package com.edu.wing.category.service;

import com.edu.wing.category.domain.CategoryVo;

import java.util.List;
import java.util.Map;

public interface CategoryService {

  List<CategoryVo> categorySelectList();

  Map<String, Object> allCategorySelectList();

  boolean categoryExists(Map<String, String> categoryMap);

  boolean categoryInsertOne(Map<String, String> categoryMap);

  CategoryVo categorySelectOne(int no);

  int categoryUpdateOne(CategoryVo categoryVo);

  int categoryDeleteOne(int no);
}
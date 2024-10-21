package com.edu.wing.category.service;

import com.edu.wing.category.domain.CategoryVo;
import com.edu.wing.category.domain.MinusCategoryVo;

import java.util.List;
import java.util.Map;

public interface CategoryService {

  List<CategoryVo> categorySelectList();

  Map<String, Object> allCategorySelectList();

  int categoryInsertOne(CategoryVo categoryVo);

  CategoryVo categorySelectOne(int no);

  int categoryUpdateOne(CategoryVo categoryVo);

  int categoryDeleteOne(int no);
}
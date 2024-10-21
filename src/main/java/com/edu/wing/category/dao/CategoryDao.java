package com.edu.wing.category.dao;

import com.edu.wing.category.domain.CategoryVo;
import com.edu.wing.category.domain.MinusCategoryVo;

import java.util.List;
import java.util.Map;

public interface CategoryDao {

  List<CategoryVo> categorySelectList();

  Map<String, Object> allCategorySelectList();

  int categoryInsertOne(CategoryVo categoryVo);

  CategoryVo categorySelectOne(int no);

  int categoryUpdateOne(CategoryVo categoryVo);

  int categoryDeleteOne(int no);
}

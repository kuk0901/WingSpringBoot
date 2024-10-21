package com.edu.wing.category.service;

import com.edu.wing.category.domain.CategoryVo;
import com.edu.wing.category.domain.MinusCategoryVo;

import java.util.List;

public interface CategoryService {

  List<CategoryVo> categorySelectList();

  int categoryInsertOne(CategoryVo categoryVo);

  CategoryVo categorySelectOne(int no);

  int categoryUpdateOne(CategoryVo categoryVo);

  int categoryDeleteOne(int no);
}
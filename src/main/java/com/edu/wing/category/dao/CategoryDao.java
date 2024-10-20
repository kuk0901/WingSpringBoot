package com.edu.wing.category.dao;

import com.edu.wing.category.domain.CategoryVo;
import com.edu.wing.category.domain.MinusCategoryVo;

import java.util.List;

public interface CategoryDao {

    List<CategoryVo> categorySelectList();

    int categoryInsertOne(CategoryVo categoryVo);

    MinusCategoryVo minusCategorySelectOne(int no);

    int minusCategoryUpdateOne(MinusCategoryVo minusCategoryVo);

    int minusCategoryDeleteOne(int no);
}

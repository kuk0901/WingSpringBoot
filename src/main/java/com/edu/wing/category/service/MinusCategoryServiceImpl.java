package com.edu.wing.category.service;

import com.edu.wing.category.dao.CategoryDao;
import com.edu.wing.category.domain.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Qualifier("minusCategoryService")
public class MinusCategoryServiceImpl implements CategoryService {

  @Autowired
  @Qualifier("minusCategoryDao")
  private CategoryDao minusCategoryDao;

  @Override
  public List<CategoryVo> categorySelectList() {
    return minusCategoryDao.categorySelectList();
  }

  @Override
  public Map<String, Object> allCategorySelectList() {
    return minusCategoryDao.allCategorySelectList();
  }

  @Override
  public int categoryInsertOne(CategoryVo categoryVo) {
    return minusCategoryDao.categoryInsertOne(categoryVo);
  }

  @Override
  public CategoryVo categorySelectOne(int no) {
    return minusCategoryDao.categorySelectOne(no);
  }

  @Override
  public int categoryUpdateOne(CategoryVo categoryVo) {
    return minusCategoryDao.categoryUpdateOne(categoryVo);
  }

  @Override
  public int categoryDeleteOne(int no) {
    return minusCategoryDao.categoryDeleteOne(no);
  }
}
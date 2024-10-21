package com.edu.wing.category.service;

import com.edu.wing.category.dao.CategoryDao;
import com.edu.wing.category.domain.CategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("plusCategoryService")
public class PlusCategoryServiceImpl implements CategoryService {

  @Autowired
  @Qualifier("plusCategoryDao")
  private CategoryDao plusCategoryDao;

  @Override
  public List<CategoryVo> categorySelectList() {
    return plusCategoryDao.categorySelectList();
  }

  @Override
  public int categoryInsertOne(CategoryVo categoryVo) {
    return plusCategoryDao.categoryInsertOne(categoryVo);
  }

  @Override
  public CategoryVo categorySelectOne(int no) {
    return plusCategoryDao.categorySelectOne(no);
  }

  @Override
  public int categoryUpdateOne(CategoryVo categoryVo) {
    return plusCategoryDao.categoryUpdateOne(categoryVo);
  }

  @Override
  public int categoryDeleteOne(int no) {
    return plusCategoryDao.categoryDeleteOne(no);
  }
}
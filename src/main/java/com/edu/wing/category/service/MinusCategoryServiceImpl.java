package com.edu.wing.category.service;


import com.edu.wing.category.dao.CategoryDao;
import com.edu.wing.category.domain.CategoryVo;
import com.edu.wing.category.domain.MinusCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("minusCategoryService")
public class MinusCategoryServiceImpl implements CategoryService {

  @Autowired
  public CategoryDao minusCategoryDao;

//    public List<CategoryVo> minusCategorySelectList(){
//
//
//        return null;
//    }

  @Override
  public List<CategoryVo> categorySelectList(){
    return minusCategoryDao.categorySelectList();
  }

  @Override
  public int categoryInsertOne(CategoryVo categoryVo) {
    // TODO Auto-generated method stub
    return minusCategoryDao.categoryInsertOne(categoryVo);
  }

  @Override
  public MinusCategoryVo minusCategorySelectOne(int no) {
    // TODO Auto-generated method stub
    return minusCategoryDao.minusCategorySelectOne(no);
  }

  @Override
  public int minusCategoryUpdateOne(MinusCategoryVo minusCategoryVo) {
    // TODO Auto-generated method stub
    return minusCategoryDao.minusCategoryUpdateOne(minusCategoryVo);
  }

  @Override
  public int minusCategoryDeleteOne(int no) {
    // TODO Auto-generated method stub
    return minusCategoryDao.minusCategoryDeleteOne(no);
  }
}

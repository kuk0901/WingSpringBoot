package com.edu.wing.category.service;

import com.edu.wing.category.dao.MinusCategoryDao;
import com.edu.wing.category.domain.MinusCategoryVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Qualifier("minusCategoryService")
public class MinusCategoryServiceImpl implements MinusCategoryService {

  @Autowired
  private MinusCategoryDao minusCategoryDao;

  @Override
  public List<MinusCategoryVo> minusCategorySelectList() {
    return minusCategoryDao.minusCategorySelectList();
  }

  @Override
  public Map<String, Object> allCategorySelectList() {
    return minusCategoryDao.allCategorySelectList();
  }

  @Override
  public boolean minusCategoryExists(String categoryName) {
    MinusCategoryVo minusCategoryVo = minusCategoryDao.minusCategoryExists(categoryName);

    return minusCategoryVo != null;
  }

  @Override
  public boolean minusCategoryInsertOne(String categoryName) {
    minusCategoryDao.minusCategoryInsertOne(categoryName);

    int categoryNo = minusCategoryDao.selectMinusCategoryNoByName(categoryName);

    return categoryNo > 0;

  }

  @Override
  public MinusCategoryVo minusCategoryUpdateOne(MinusCategoryVo minusCategoryVo) {
    return minusCategoryDao.minusCategoryUpdateOne(minusCategoryVo);
  }

  @Override
  public MinusCategoryVo minusCategorySelectOne(int categoryNo) {
    return minusCategoryDao.minusCategorySelectOne(categoryNo);
  }

  @Override
  public int minusCategoryTotalCount(int categoryNo){
    return minusCategoryDao.minusCategoryTotalCount(categoryNo);
  }

  @Override
  public boolean minusCategoryDeleteOne(int categoryNo) {
    return minusCategoryDao.minusCategoryDeleteOne(categoryNo);
  }
}
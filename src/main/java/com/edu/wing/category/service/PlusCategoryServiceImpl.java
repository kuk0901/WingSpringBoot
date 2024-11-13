package com.edu.wing.category.service;

import com.edu.wing.category.dao.PlusCategoryDao;
import com.edu.wing.category.domain.PlusCategoryVo;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Qualifier("plusCategoryService")
public class PlusCategoryServiceImpl implements PlusCategoryService {

  @Autowired
  private PlusCategoryDao plusCategoryDao;
  @Autowired
  private SqlSessionFactory sqlSessionFactory;

  @Override
  public List<PlusCategoryVo> plusCategorySelectList() {
    return plusCategoryDao.plusCategorySelectList();
  }

  @Override
  public List<String> getPlusCategoryNames(){
    return plusCategoryDao.getPlusCategoryNames();
  }

  @Override
  public boolean plusCategoryExists (String categoryName) {
    PlusCategoryVo plusCategoryVo = plusCategoryDao.plusCategoryExists(categoryName);

    return plusCategoryVo != null;
  }

  @Override
  public boolean plusCategoryInsertOne(String categoryName) {
    plusCategoryDao.plusCategoryInsertOne(categoryName);

    int plusCategoryNo = plusCategoryDao.selectPlusCategoryNoByName(categoryName);

    return plusCategoryNo > 0;
  }

  @Override
  public boolean plusCategoryUpdateOne(PlusCategoryVo plusCategoryVo) {
    return plusCategoryDao.plusCategoryUpdateOne(plusCategoryVo) > 0;
  }

  @Override
  public PlusCategoryVo plusCategorySelectOne(int categoryNo) {
    return plusCategoryDao.plusCategorySelectOne(categoryNo);
  }

  @Override
  public int plusCategoryTotalCount(int categoryNo) {
    return plusCategoryDao.plusCategoryTotalCount(categoryNo);
  }

  @Override
  public boolean plusCategoryDeleteOne(int categoryNo) {
    return plusCategoryDao.plusCategoryDeleteOne(categoryNo);
  }
}
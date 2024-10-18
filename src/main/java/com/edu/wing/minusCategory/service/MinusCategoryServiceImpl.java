package com.edu.wing.minusCategory.service;


import com.edu.wing.minusCategory.dao.MinusCategoryDao;
import com.edu.wing.minusCategory.domain.MinusCategoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MinusCategoryServiceImpl implements MinusCategoryService{

    @Autowired
    public MinusCategoryDao minusCategoryDao;

    @Override
    public List<MinusCategoryVo> minusCategorySelectList(){
        return minusCategoryDao.minusCategorySelectList();
    }
}

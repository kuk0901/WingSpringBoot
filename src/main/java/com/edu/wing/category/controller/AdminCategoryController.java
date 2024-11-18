package com.edu.wing.category.controller;

import com.edu.wing.category.domain.MinusCategoryVo;
import com.edu.wing.category.domain.PlusCategoryVo;
import com.edu.wing.category.service.MinusCategoryService;
import com.edu.wing.category.service.PlusCategoryService;
import com.edu.wing.paymentMethod.domain.PaymentMethodVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {
  @Autowired
  private MinusCategoryService minusCategoryService;

  @Autowired
  private PlusCategoryService plusCategoryService;

  @GetMapping(value = "/list")
  public ModelAndView categoryList() {

    List<MinusCategoryVo> minusCategoryList = minusCategoryService.minusCategorySelectList();
    List<PlusCategoryVo> plusCategoryList = plusCategoryService.plusCategorySelectList();

    ModelAndView mav = new ModelAndView("jsp/admin/category/CategoryListView");
    mav.addObject("minusCategoryList", minusCategoryList);
    mav.addObject("plusCategoryList", plusCategoryList);

    return mav;
  }

  @GetMapping("/list/add")
  public ModelAndView categoryAdd() {

    List<String> minusCategories = minusCategoryService.getMinusCategoryNames();
    List<String> plusCategories = plusCategoryService.getPlusCategoryNames();

    ModelAndView mav = new ModelAndView("jsp/admin/category/CategoryAddView");
    mav.addObject("minusCategories", minusCategories);
    mav.addObject("plusCategories", plusCategories);

    return mav;
  }

}


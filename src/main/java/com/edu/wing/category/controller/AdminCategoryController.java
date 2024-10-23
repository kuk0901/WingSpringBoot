package com.edu.wing.category.controller;


import com.edu.wing.category.domain.CategoryVo;
import com.edu.wing.category.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {

  private static final Logger log = LoggerFactory.getLogger(AdminCategoryController.class);
  private static final String LOG_TITLE = "==AdminCategoryController==";

  @Autowired
  @Qualifier("minusCategoryService")
  private CategoryService minusCategoryService;

  @Autowired
  @Qualifier("plusCategoryService")
  private CategoryService plusCategoryService;

  @GetMapping(value = "/list")
  public ModelAndView categoryList() {
    log.info("{} - Retrieving all categories", LOG_TITLE);

    List<CategoryVo> minusCategoryList = minusCategoryService.categorySelectList();
    List<CategoryVo> plusCategoryList = plusCategoryService.categorySelectList();

    ModelAndView mav = new ModelAndView("jsp/admin/category/CategoryListView");
    mav.addObject("minusCategoryList", minusCategoryList);
    mav.addObject("plusCategoryList", plusCategoryList);

    return mav;
  }

  @GetMapping("/add")
  public ModelAndView categoryAdd() {
    log.info("{} - Showing category add form", LOG_TITLE);

    return new ModelAndView("jsp/admin/category/CategoryAddView");
  }

  @GetMapping("/update")
  public String categoryUpdate(@RequestParam("type") String type, @RequestParam("categoryNo") int categoryNo, Model model) {
    log.info("{} - Showing update form for category: {}", LOG_TITLE, categoryNo);

    CategoryVo categoryVo = null;
    if ("minus".equals(type)) {
      categoryVo = minusCategoryService.categorySelectOne(categoryNo);
    } else {
      categoryVo = plusCategoryService.categorySelectOne(categoryNo);
    }

    model.addAttribute("categoryVo", categoryVo);
    model.addAttribute("type", type);

    return "jsp/admin/api/category/CategoryUpdateView";
  }

}

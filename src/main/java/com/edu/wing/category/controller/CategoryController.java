package com.edu.wing.category.controller;


import com.edu.wing.category.domain.CategoryVo;
import com.edu.wing.category.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/api/category")
public class CategoryController {

  private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
  private static final String LOG_TITLE = "==AdminCategoryController==";

  @Autowired
  @Qualifier("minusCategoryService")
  private CategoryService minusCategoryService;

  @Autowired
  @Qualifier("plusCategoryService")
  private CategoryService plusCategoryService;

//  @GetMapping(value = "/list")
//  public ResponseEntity<?> categoryList() {
//    log.info("{} - Retrieving all categories", LOG_TITLE);
//
//    Map<String, Object> resultMap = new HashMap<>();
//
//    // minusCategoryService의 allCategorySelectList() 호출
//    Map<String, Object> minusCategories = minusCategoryService.allCategorySelectList();
//    resultMap.putAll(minusCategories);
//
//    // plusCategoryService의 allCategorySelectList() 호출
//    Map<String, Object> plusCategories = plusCategoryService.allCategorySelectList();
//    resultMap.putAll(plusCategories);
//
//    return ResponseEntity.ok().body(resultMap);
//  }

  @GetMapping(value = "/list")
  public ResponseEntity<?> categoryList() {
    log.info("{} - Retrieving all categories", LOG_TITLE);

    Map<String, Object> minusCategories = minusCategoryService.allCategorySelectList();
    Map<String, Object> plusCategories = plusCategoryService.allCategorySelectList();

    Map<String, Object> resultMap = new HashMap<>();
    resultMap.putAll(minusCategories);
    resultMap.putAll(plusCategories);

    return ResponseEntity.ok().body(resultMap);
  }

  @GetMapping("/add")
  public String categoryAdd(Model model) {
    log.info("{} - Showing category add form", LOG_TITLE);
    return "jsp/admin/category/CategoryAdd";
  }

  @PostMapping("/add")
  public String categoryAdd(@RequestParam("type") String type, CategoryVo categoryVo, Model model) {
    log.info("{} - Adding new category: {}", LOG_TITLE, categoryVo);

    if ("minus".equals(type)) {
      minusCategoryService.categoryInsertOne(categoryVo);
    } else if ("plus".equals(type)) {
      plusCategoryService.categoryInsertOne(categoryVo);
    }

    return "redirect:/admin/api/category/list";
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

  @PostMapping(value = "/update")
  public String categoryUpdate(@RequestParam("type") String type, CategoryVo categoryVo, RedirectAttributes redirectAttributes) {
    log.info("{} - Updating category: {}", LOG_TITLE, categoryVo);

    if ("minus".equals(type)) {
      minusCategoryService.categoryUpdateOne(categoryVo);
    } else if ("plus".equals(type)) {
      plusCategoryService.categoryUpdateOne(categoryVo);
    }

    redirectAttributes.addAttribute("categoryNo", categoryVo.getCategoryNo());

    return "redirect:/admin/api/category/list";
  }

  @DeleteMapping("/delete/{type}/{categoryNo}")
  public ResponseEntity<String> categoryDelete(@PathVariable("type") String type, @PathVariable("categoryNo") int categoryNo) {
    log.info("{} - Deleting category: {} of type: {}", LOG_TITLE, categoryNo, type);

    if ("minus".equals(type)) {
      minusCategoryService.categoryDeleteOne(categoryNo);
    } else if ("plus".equals(type)) {
      plusCategoryService.categoryDeleteOne(categoryNo);
    }

    return ResponseEntity.ok("카테고리 삭제 성공");
  }
}

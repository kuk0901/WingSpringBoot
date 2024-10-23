package com.edu.wing.category.controller;


import com.edu.wing.category.domain.CategoryVo;
import com.edu.wing.category.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/category")
public class AdminApiCategoryController {

  private static final Logger log = LoggerFactory.getLogger(AdminApiCategoryController.class);
  private static final String LOG_TITLE = "==AdminCategoryController==";

  @Autowired
  @Qualifier("minusCategoryService")
  private CategoryService minusCategoryService;

  @Autowired
  @Qualifier("plusCategoryService")
  private CategoryService plusCategoryService;


  @PostMapping("/category/add")
  public ResponseEntity<?> addCategory(@RequestParam String categoryName, @RequestParam String division) {
    log.info("카테고리 추가: 이름 = {}, 구분 = {}", categoryName, division);

    Map<String, String> resultMap = new HashMap<>();
    Map<String, String> categoryMap = new HashMap<>();

    try {
      boolean categoryExists;
      boolean isAdded;

      categoryMap.put("categoryName", categoryName);

      if ("plus".equals(division)) {
        categoryMap.put("division", "+");
        categoryExists = plusCategoryService.categoryExists(categoryMap);
        if (categoryExists) {
          resultMap.put("status", "fail");
          resultMap.put("msg", "이미 존재하는 수입 카테고리입니다.");
          return ResponseEntity.status(HttpStatus.CONFLICT).body(resultMap);
        }
        isAdded = plusCategoryService.categoryInsertOne(categoryMap);
      } else if ("minus".equals(division)) {
        categoryMap.put("division", "-");
        categoryExists = minusCategoryService.categoryExists(categoryMap);
        if (categoryExists) {
          resultMap.put("status", "fail");
          resultMap.put("msg", "이미 존재하는 지출 카테고리입니다.");
          return ResponseEntity.status(HttpStatus.CONFLICT).body(resultMap);
        }
        isAdded = minusCategoryService.categoryInsertOne(categoryMap);
      } else {
        resultMap.put("status", "fail");
        resultMap.put("msg", "잘못된 카테고리 구분입니다.");
        return ResponseEntity.badRequest().body(resultMap);
      }

      if (isAdded) {
        resultMap.put("status", "success");
        resultMap.put("msg", "카테고리가 성공적으로 추가되었습니다.");
        return ResponseEntity.ok().body(resultMap);
      }

      resultMap.put("status", "fail");
      resultMap.put("msg", "카테고리 추가에 실패했습니다.");
      return ResponseEntity.internalServerError().body(resultMap);

    } catch (Exception e) {
      log.error("카테고리 추가 중 오류 발생", e);
      resultMap.put("status", "error");
      resultMap.put("msg", "서버 오류가 발생했습니다.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
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

    return "redirect:/admin/category/list";
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

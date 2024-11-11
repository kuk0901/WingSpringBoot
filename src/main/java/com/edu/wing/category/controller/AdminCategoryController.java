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

  private static final Logger log = LoggerFactory.getLogger(AdminCategoryController.class);
  private static final String LOG_TITLE = "==AdminCategoryController==";

  @Autowired
  private MinusCategoryService minusCategoryService;

  @Autowired
  private PlusCategoryService plusCategoryService;

  @GetMapping(value = "/list")
  public ModelAndView categoryList() {
    log.info("{} - Retrieving all categories", LOG_TITLE);

    List<MinusCategoryVo> minusCategoryList = minusCategoryService.minusCategorySelectList();
    List<PlusCategoryVo> plusCategoryList = plusCategoryService.plusCategorySelectList();

    ModelAndView mav = new ModelAndView("jsp/admin/category/CategoryListView");
    mav.addObject("minusCategoryList", minusCategoryList);
    mav.addObject("plusCategoryList", plusCategoryList);

    return mav;
  }

  @GetMapping("/add")
  public ModelAndView categoryAdd() {
    log.info("{} - Showing category add form", LOG_TITLE);

    List<String> minusCategories = minusCategoryService.getMinusCategoryNames();
    List<String> plusCategories = plusCategoryService.getPlusCategoryNames();

    ModelAndView mav = new ModelAndView("jsp/admin/category/CategoryAddView");
    mav.addObject("minusCategories", minusCategories);
    mav.addObject("plusCategories", plusCategories);

    return mav;
  }

  @GetMapping("/countPlusCategory/{categoryNo}")
  public ResponseEntity<?> getPlusCategoryCount(@PathVariable int categoryNo) {
    log.info(LOG_TITLE);
    log.info("getPlusCategoryTotalCount categoryNo: {}", categoryNo);

    Map<String, Object> resultMap = new HashMap<>();

    int count = plusCategoryService.plusCategoryTotalCount(categoryNo);

    if (count > 0) {
      resultMap.put("status", "success");
      resultMap.put("totalCount", count);
      resultMap.put("alertMsg", "해당 카테고리로 작성된 가계부의 게시글이 " + count + "개입니다. 해당 카테고리를 수정할 수 없습니다.");

      return ResponseEntity.ok().body(resultMap);
    }

    return ResponseEntity.ok().body(resultMap);
  }

  @GetMapping("/countMinusCategory/{categoryNo}")
  public ResponseEntity<?> getMinusCategoryCount(@PathVariable int categoryNo) {
    log.info(LOG_TITLE);
    log.info("getMinusCategoryTotalCount categoryNo: {}", categoryNo);

    Map<String, Object> resultMap = new HashMap<>();

    int count = minusCategoryService.minusCategoryTotalCount(categoryNo);

    if (count > 0) {
      resultMap.put("status", "success");
      resultMap.put("totalCount", count);
      resultMap.put("alertMsg", "해당 카테고리로 작성된 가계부의 게시글이 " + count + "개입니다. 해당 카테고리를 수정할 수 없습니다.");

      return ResponseEntity.ok().body(resultMap);
    }

    return ResponseEntity.ok().body(resultMap);
  }

  @GetMapping("/deletePlusCategory/{categoryNo}")
  public ResponseEntity<?> getPlusCategoryTotalCount(@PathVariable int categoryNo) {
    log.info(LOG_TITLE);
    log.info("getPlusCategoryTotalCount categoryNo: {}", categoryNo);

    Map<String, Object> resultMap = new HashMap<>();

    int count = plusCategoryService.plusCategoryTotalCount(categoryNo);

    if (count > 0) {
      resultMap.put("status", "success");
      resultMap.put("totalCount", count);
      resultMap.put("alertMsg", "해당 카테고리로 작성된 가계부의 게시글이 " + count + "개입니다. 해당 카테고리를 삭제할 수 없습니다.");

      return ResponseEntity.ok().body(resultMap);
    }

    return ResponseEntity.ok().body(resultMap);
  }

  @GetMapping("/deleteMinusCategory/{categoryNo}")
  public ResponseEntity<?> getMinusCategoryTotalCount(@PathVariable int categoryNo) {
    log.info(LOG_TITLE);
    log.info("getMinusCategoryTotalCount categoryNo: {}", categoryNo);

    Map<String, Object> resultMap = new HashMap<>();

    int count = minusCategoryService.minusCategoryTotalCount(categoryNo);

    if (count > 0) {
      resultMap.put("status", "success");
      resultMap.put("totalCount", count);
      resultMap.put("alertMsg", "해당 카테고리로 작성된 가계부의 게시글이 " + count + "개입니다. 해당 카테고리를 삭제할 수 없습니다.");

      return ResponseEntity.ok().body(resultMap);
    }

    return ResponseEntity.ok().body(resultMap);
  }
}


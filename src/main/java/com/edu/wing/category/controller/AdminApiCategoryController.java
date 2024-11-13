package com.edu.wing.category.controller;

import com.edu.wing.category.domain.MinusCategoryVo;
import com.edu.wing.category.domain.PlusCategoryVo;
import com.edu.wing.category.service.MinusCategoryService;
import com.edu.wing.category.service.PlusCategoryService;
import com.edu.wing.paymentMethod.domain.PaymentMethodVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
  private MinusCategoryService minusCategoryService;

  @Autowired
  private PlusCategoryService plusCategoryService;

  @PostMapping("/addPlus")
  public ResponseEntity<?> addPlusCategory(@RequestParam String categoryName) {
    log.info("카테고리 추가 요청: 이름 = {}", categoryName);

    Map<String, String> resultMap = new HashMap<>();

    if (plusCategoryService.plusCategoryExists(categoryName)) {
      resultMap.put("status", "fail");
      resultMap.put("alertMsg", "이미 존재하는 카테고리입니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    // 새로운 결제 방법 추가
    boolean isAdded = plusCategoryService.plusCategoryInsertOne(categoryName);
    if (!isAdded) {
      resultMap.put("status", "fail");
      resultMap.put("alertMsg", "카테고리 추가에 실패했습니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "카테고리가 성공적으로 추가되었습니다.");
    return ResponseEntity.ok().body(resultMap);
  }

  @PostMapping("/addMinus")
  public ResponseEntity<?> addMinusCategory(@RequestParam String categoryName) {
    log.info("카테고리 추가 요청: 이름 = {}", categoryName);

    Map<String, String> resultMap = new HashMap<>();

    if (minusCategoryService.minusCategoryExists(categoryName)) {
      resultMap.put("status", "fail");
      resultMap.put("alertMsg", "이미 존재하는 카테고리명 입니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    // 새로운 결제 방법 추가
    boolean isAdded = minusCategoryService.minusCategoryInsertOne(categoryName);
    if (!isAdded) {
      resultMap.put("status", "fail");
      resultMap.put("alertMsg", "카테고리 추가에 실패했습니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "카테고리가 성공적으로 추가되었습니다.");
    return ResponseEntity.ok().body(resultMap);

  }


  @PatchMapping("/updatePlus/{categoryNo}")
  public ResponseEntity<?> plusCategoryUpdateOne(@PathVariable int categoryNo, @RequestParam Map<String, String> plusCategoryMap) {

    PlusCategoryVo plusCategoryVo = new PlusCategoryVo();
    plusCategoryVo.setCategoryNo(categoryNo);
    plusCategoryVo.setCategoryName(plusCategoryMap.get("categoryName"));

    Map<String, Object> resultMap = new HashMap<>();

    boolean plusCategoryUpdate = plusCategoryService.plusCategoryUpdateOne(plusCategoryVo);

    if(!plusCategoryUpdate) {
      resultMap.put("status", "fail");
      resultMap.put("alertMsg", "서버 오류로 인해 카테고리가 수정되지 않았습니다. 잠시 후 다시 시도해주세요.");
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "카테고리가 성공적으로 수정되었습니다");
    return ResponseEntity.ok().body(resultMap);
  }

  @PatchMapping("/updateMinus/{categoryNo}")
  public ResponseEntity<?> minusCategoryUpdateOne(@PathVariable int categoryNo,
                                                  @RequestParam Map<String, String> minusCategoryMap) {

    Map<String, Object> resultMap = new HashMap<>();

    MinusCategoryVo minusCategoryVo = new MinusCategoryVo();

    minusCategoryVo.setCategoryNo(categoryNo);
    minusCategoryVo.setCategoryName(minusCategoryMap.get("categoryName"));

    boolean minusCategoryUpdate = minusCategoryService.minusCategoryUpdateOne(minusCategoryVo);

    if(!minusCategoryUpdate) {
      resultMap.put("status", "fail");
      resultMap.put("alertMsg", "서버 오류로 인해 카테고리가 수정되지 않았습니다. 잠시 후 다시 시도해주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "카테고리가 성공적으로 수정되었습니다.");
    return ResponseEntity.ok().body(resultMap);
  }

  @PostMapping("/updatePlus/{categoryNo}")
  public ResponseEntity<?> updatePlusCategory(@PathVariable int categoryNo) {
    log.info(LOG_TITLE);
    log.info("updatePlusCategory Get categoryNo: {}", categoryNo);

    Map<String, Object> resultMap = new HashMap<>();

    PlusCategoryVo plusCategoryVo = plusCategoryService.plusCategorySelectOne(categoryNo);

    return ResponseEntity.ok().body(plusCategoryVo);
  }

  @PostMapping("/updateMinus/{categoryNo}")
  public ResponseEntity<?> updateMinusCategory(@PathVariable int categoryNo) {
    log.info(LOG_TITLE);
    log.info("updateMinusCategory Get categoryNo: {}", categoryNo);

    Map<String, Object> resultMap = new HashMap<>();

    MinusCategoryVo minusCategoryVo = minusCategoryService.minusCategorySelectOne(categoryNo);

    return ResponseEntity.ok().body(minusCategoryVo);
  }

  @DeleteMapping("/deletePlusCategory/{categoryNo}")
  public ResponseEntity<?> deletePlusCategory(@PathVariable int categoryNo) {
    log.info(LOG_TITLE);
    log.info("deletePlusCategory categoryNo: {}", categoryNo);

    Map<String, Object> resultMap = new HashMap<>();
    PlusCategoryVo plusCategoryVo = plusCategoryService.plusCategorySelectOne(categoryNo);


    if (plusCategoryVo == null) {
      resultMap.put("status", "error");
      resultMap.put("alertMsg", "카테고리를 찾을 수 없습니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("categoryName", plusCategoryVo.getCategoryName());
    resultMap.put("alertMsg", "카테고리를 삭제할 수 있습니다.");
    return ResponseEntity.ok(resultMap);
  }

  @DeleteMapping("/finalDeletePlusCategory/{categoryNo}")
  public ResponseEntity<?> finalDeletePlusCategory(@PathVariable int categoryNo) {
    log.info("{} - Final deletion for categoryNo: {}", LOG_TITLE, categoryNo);

    Map<String, Object> resultMap = new HashMap<>();
    PlusCategoryVo plusCategoryVo = plusCategoryService.plusCategorySelectOne(categoryNo);

    if (plusCategoryVo == null) {
      resultMap.put("status", "error");
      resultMap.put("alertMsg", "카테고리를 찾을 수 없습니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    boolean isDeleted = plusCategoryService.plusCategoryDeleteOne(categoryNo);

    if (!isDeleted) {
      resultMap.put("status", "error");
      resultMap.put("alertMsg", "카테고리 삭제에 실패했습니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "'" + plusCategoryVo.getCategoryName() + "' 카테고리가 성공적으로 삭제되었습니다.");
    return ResponseEntity.ok(resultMap);

  }

  @DeleteMapping("/deleteMinusCategory/{categoryNo}")
  public ResponseEntity<?> deleteMinusCategory(@PathVariable int categoryNo) {
    log.info(LOG_TITLE);
    log.info("deleteMinusCategory categoryNo: {}", categoryNo);

    Map<String, Object> resultMap = new HashMap<>();
    MinusCategoryVo minusCategoryVo = minusCategoryService.minusCategorySelectOne(categoryNo);

    if (minusCategoryVo == null) {
      resultMap.put("status", "error");
      resultMap.put("message", "카테고리를 찾을 수 없습니다.");
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("categoryName", minusCategoryVo.getCategoryName());
    resultMap.put("message", "카테고리를 삭제할 수 있습니다.");
    return ResponseEntity.ok(resultMap);
  }

  @DeleteMapping("/finalDeleteMinusCategory/{categoryNo}")
  public ResponseEntity<?> finalDeleteMinusCategory(@PathVariable int categoryNo) {
    log.info("{} - Final deletion for categoryNo: {}", LOG_TITLE, categoryNo);

    Map<String, Object> resultMap = new HashMap<>();
    MinusCategoryVo minusCategoryVo = minusCategoryService.minusCategorySelectOne(categoryNo);

    if (minusCategoryVo == null) {
      resultMap.put("status", "error");
      resultMap.put("alertMsg", "카테고리를 찾을 수 없습니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    boolean isDeleted = minusCategoryService.minusCategoryDeleteOne(categoryNo);

    if (!isDeleted) {
      resultMap.put("status", "error");
      resultMap.put("alertMsg", "카테고리 삭제에 실패했습니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "'" + minusCategoryVo.getCategoryName() + "' 카테고리가 성공적으로 삭제되었습니다.");
    return ResponseEntity.ok(resultMap);

  }
}

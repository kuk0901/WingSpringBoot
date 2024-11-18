package com.edu.wing.paymentMethod.controller;

import com.edu.wing.category.controller.AdminCategoryController;
import com.edu.wing.paymentMethod.domain.PaymentMethodVo;
import com.edu.wing.paymentMethod.service.PaymentMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/api/paymentMethod")
public class AdminApiPaymentMethodController {

  @Autowired
  private PaymentMethodService paymentMethodService;

  @PostMapping("/add")
  public ResponseEntity<?> addPaymentMethod(@RequestParam String paymentMethodName) {

    Map<String, String> resultMap = new HashMap<>();

    try {
      // 결제 방법 이름이 이미 존재하는지 확인
      if (paymentMethodService.paymentMethodExist(paymentMethodName)) {
        resultMap.put("status", "fail");
        resultMap.put("alertMsg", "이미 존재하는 결제 수단 이름입니다.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resultMap);
      }

      // 새로운 결제 방법 추가
      boolean isAdded = paymentMethodService.addPaymentMethod(paymentMethodName);
      if (isAdded) {
        resultMap.put("status", "success");
        resultMap.put("alertMsg", "결제 수단이 성공적으로 추가되었습니다.");
        return ResponseEntity.ok().body(resultMap);
      }

      resultMap.put("status", "fail");
      resultMap.put("alertMsg", "결제 수단 추가에 실패했습니다.");
      return ResponseEntity.internalServerError().body(resultMap);

    } catch (Exception e) {
      resultMap.put("status", "error");
      resultMap.put("alertMsg", "서버 오류가 발생했습니다.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
  }

  @GetMapping("/delete/{paymentMethodNo}")
  public ResponseEntity<?> getPaymentMethodTotalCount(@PathVariable int paymentMethodNo) {

    Map<String, Object> resultMap = new HashMap<>();

    int count = paymentMethodService.pmTotalCount(paymentMethodNo);

    if (count > 0) {
      resultMap.put("status", "success");
      resultMap.put("totalCount", count);
      resultMap.put("msg", "해당 결제수단으로 작성된 가계부의 게시글이 " + count + "개입니다. 해당 결제수단을 삭제할 수 없습니다.");

      return ResponseEntity.ok().body(resultMap);
    }

    return ResponseEntity.ok().body(resultMap);
  }

  @DeleteMapping("/delete/{paymentMethodNo}")
  public ResponseEntity<?> deletePaymentMethod(@PathVariable int paymentMethodNo) {

    Map<String, Object> resultMap = new HashMap<>();
    PaymentMethodVo paymentMethod = paymentMethodService.paymentMethodSelectOne(paymentMethodNo);

    if (paymentMethod == null) {
      resultMap.put("status", "error");
      resultMap.put("alertMsg", "결제 방법을 찾을 수 없습니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("paymentMethodName", paymentMethod.getPaymentMethodName());
    resultMap.put("alertMsg", "결제 방법을 삭제할 수 있습니다.");
    return ResponseEntity.ok(resultMap);
  }

  @DeleteMapping("/finalDelete/{paymentMethodNo}")
  public ResponseEntity<?> finalDeletePaymentMethod(@PathVariable int paymentMethodNo) {

    Map<String, Object> resultMap = new HashMap<>();

    PaymentMethodVo paymentMethod = paymentMethodService.paymentMethodSelectOne(paymentMethodNo);

    if (paymentMethod == null) {
      resultMap.put("status", "error");
      resultMap.put("alertMsg", "결제 방법을 찾을 수 없습니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    boolean isDeleted = paymentMethodService.paymentMethodDeleteOne(paymentMethodNo);

    if (isDeleted) {
      resultMap.put("status", "success");
      resultMap.put("alertMsg", "'" + paymentMethod.getPaymentMethodName() + "' 결제 방법이 성공적으로 삭제되었습니다.");
      return ResponseEntity.ok().body(resultMap);
    } else {
      resultMap.put("status", "error");
      resultMap.put("alertMsg", "결제 방법 삭제에 실패했습니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }
  }

  @PatchMapping("/update/{paymentMethodNo}")
  public ResponseEntity<?> paymentMethodUpdateOne(@PathVariable int paymentMethodNo,
    @RequestParam Map<String, String> paymentMethodMap, @RequestParam String paymentMethodName) {

    Map<String, Object> resultMap = new HashMap<>();

    PaymentMethodVo paymentMethodVo = new PaymentMethodVo();
    paymentMethodVo.setPaymentMethodNo(paymentMethodNo);
    paymentMethodVo.setPaymentMethodName(paymentMethodMap.get("paymentMethodName"));

    if (paymentMethodService.paymentMethodExist(paymentMethodName)) {
      resultMap.put("status", "fail");
      resultMap.put("alertMsg", "이미 존재하는 결제 수단 이름입니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    paymentMethodService.paymentMethodUpdateOne(paymentMethodVo);

    if (paymentMethodService.paymentMethodSelectOne(paymentMethodNo) != null) {
      resultMap.put("status", "success");
      resultMap.put("alertMsg", "결제 수단 명이 변경되었습니다.");
      return ResponseEntity.ok().body(resultMap);
    }

    resultMap.put("status", "failed");
    resultMap.put("alertMsg", "서버 오류로 인해 결제 수단 명이 변경되지 않았습니다. 잠시 후 다시 시도해 주세요.");
    return ResponseEntity.badRequest().body(resultMap);
  }

  @GetMapping("/countPaymentMethod/{paymentMethodNo}")
  public ResponseEntity<?> getPaymentMethodCount(@PathVariable int paymentMethodNo) {

    Map<String, Object> resultMap = new HashMap<>();

    if (paymentMethodNo == 3) {
      resultMap.put("status", "success");
      resultMap.put("msg", "해당 결제 수단은 수정이 불가능합니다.");

      return ResponseEntity.ok().body(resultMap);
    }

    int totalCount = paymentMethodService.pmTotalCount(paymentMethodNo);

    if (totalCount > 0) {
      resultMap.put("status", "success");
      resultMap.put("totalCount", totalCount);
      resultMap.put("msg", "해당 결제 수단으로 작성된 가계부의 게시글이 " + totalCount + "개입니다. 해당 결제 수단을 수정할 수 없습니다.");

      return ResponseEntity.ok().body(resultMap);
    }

    return ResponseEntity.ok().body(resultMap);
  }

  @GetMapping("/update/{paymentMethodNo}")
  public ResponseEntity<?> updatePaymentMethod(@PathVariable int paymentMethodNo) {

    Map<String, Object> resultMap = new HashMap<>();

    PaymentMethodVo paymentMethodVo = paymentMethodService.paymentMethodSelectOne(paymentMethodNo);

    List<String> paymentMethodList = paymentMethodService.getPaymentMethodName();

    resultMap.put("status", "success");
    resultMap.put("paymentMethodList", paymentMethodList);
    resultMap.put("paymentMethodVo", paymentMethodVo);

    return ResponseEntity.ok().body(resultMap);
  }
}

package com.edu.wing.paymentMethod.controller;

import com.edu.wing.category.controller.AdminCategoryController;
import com.edu.wing.paymentMethod.domain.PaymentMethodVo;
import com.edu.wing.paymentMethod.service.PaymentMethodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("admin/paymentMethod")
public class AdminPaymentMethodController {
  private static final Logger log = LoggerFactory.getLogger(AdminCategoryController.class);
  private static final String LOG_TITLE = "==PaymentMethodController==";

  @Autowired
  private PaymentMethodService paymentMethodService;

  @RequestMapping(value = "/list")
  public ModelAndView paymentMethodSelectList() {
    log.info("{} - Retrieving paymentMethod list", LOG_TITLE);

    List<PaymentMethodVo> paymentMethodVoList = paymentMethodService.paymentMethodSelectList();

    ModelAndView mav = new ModelAndView("jsp/admin/paymentMethod/PaymentMethodListView");
    mav.addObject("paymentMethodVoList", paymentMethodVoList);

    return mav;
  }

  @GetMapping("/list/add")
  public ModelAndView paymentMethodAdd() {
    log.info("{} - Retrieving @GetMapping paymentMethod add", LOG_TITLE);

    List<String> paymentMethodList = paymentMethodService.getPaymentMethodName();

    ModelAndView mav = new ModelAndView("jsp/admin/paymentMethod/PaymentMethodAddView");
    mav.addObject("paymentMethodList", paymentMethodList);

    return mav;
  }

  @GetMapping("/delete/{paymentMethodNo}")
  public ResponseEntity<?> getPaymentMethodTotalCount(@PathVariable int paymentMethodNo) {
    log.info(LOG_TITLE);
    log.info("paymentMethodTotalCount paymentMethodNo: {}", paymentMethodNo);

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

  @GetMapping("/countPaymentMethod/{paymentMethodNo}")
  public ResponseEntity<?> getPaymentMethodCount(@PathVariable int paymentMethodNo) {
    log.info(LOG_TITLE);
    log.info("getPaymentMethodCount paymentMethodNo: {}", paymentMethodNo);

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
    log.info(LOG_TITLE);
    log.info("updatePaymentMethod Get paymentMethodNo: {}", paymentMethodNo);

    Map<String, Object> resultMap = new HashMap<>();

    PaymentMethodVo paymentMethodVo = paymentMethodService.paymentMethodSelectOne(paymentMethodNo);

    List<String> paymentMethodList = paymentMethodService.getPaymentMethodName();

    resultMap.put("status", "success");
    resultMap.put("paymentMethodList", paymentMethodList);
    resultMap.put("paymentMethodVo", paymentMethodVo);

    return ResponseEntity.ok().body(resultMap);
  }

}

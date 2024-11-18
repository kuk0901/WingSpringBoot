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

  @Autowired
  private PaymentMethodService paymentMethodService;

  @RequestMapping(value = "/list")
  public ModelAndView paymentMethodSelectList() {

    List<PaymentMethodVo> paymentMethodVoList = paymentMethodService.paymentMethodSelectList();

    ModelAndView mav = new ModelAndView("jsp/admin/paymentMethod/PaymentMethodListView");
    mav.addObject("paymentMethodVoList", paymentMethodVoList);

    return mav;
  }

  @GetMapping("/list/add")
  public ModelAndView paymentMethodAdd() {

    List<String> paymentMethodList = paymentMethodService.getPaymentMethodName();

    ModelAndView mav = new ModelAndView("jsp/admin/paymentMethod/PaymentMethodAddView");
    mav.addObject("paymentMethodList", paymentMethodList);

    return mav;
  }

}

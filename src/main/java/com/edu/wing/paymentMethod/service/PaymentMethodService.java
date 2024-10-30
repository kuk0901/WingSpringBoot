package com.edu.wing.paymentMethod.service;

import com.edu.wing.paymentMethod.domain.PaymentMethodVo;

import java.util.List;

public interface PaymentMethodService {

  List<PaymentMethodVo> paymentMethodSelectList();

  boolean paymentMethodExist(String paymentMethodName);

  boolean addPaymentMethod(String paymentMethodName);

  int pmTotalCount(int paymentMethodNo);

  PaymentMethodVo paymentMethodSelectOne(int paymentMethodNo);

  boolean paymentMethodDeleteOne(int paymentMethodNo);

  void paymentMethodUpdateOne(PaymentMethodVo paymentMethodVo);
}

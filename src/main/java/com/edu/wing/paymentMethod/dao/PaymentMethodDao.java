package com.edu.wing.paymentMethod.dao;

import com.edu.wing.paymentMethod.domain.PaymentMethodVo;

import java.util.List;

public interface PaymentMethodDao {

  List<PaymentMethodVo> paymentMethodSelectList();

  PaymentMethodVo paymentMethodExist(String paymentMethodName);

  void insertPaymentMethod(String paymentMethodName);

  int selectPaymentMethodNoByName(String paymentMethodName);

  int pmTotalCount(int paymentMethodNo);

  PaymentMethodVo paymentMethodSelectOne(int paymentMethodNo);

  boolean paymentMethodDeleteOne(int paymentMethodNo);

  PaymentMethodVo paymentMethodUpdateOne(PaymentMethodVo paymentMethodVo);
}

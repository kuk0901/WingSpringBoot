package com.edu.wing.paymentMethod.dao;

import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.paymentMethod.domain.PaymentMethodVo;

import java.util.List;

public interface PaymentMethodDao {

  List<PaymentMethodVo> paymentMethodSelectList();

  PaymentMethodVo paymentMethodExist(String paymentMethodName);

  int countByPaymentMethodName(String paymentMethodName);

  void insertPaymentMethod(String paymentMethodName);

  int selectPaymentMethodNoByName(String paymentMethodName);

  int pmTotalCount(int paymentMethodNo);

  PaymentMethodVo paymentMethodSelectOne(int paymentMethodNo);

  boolean paymentMethodDeleteOne(int paymentMethodNo);
}

package com.edu.wing.paymentMethod.service;

import com.edu.wing.paymentMethod.dao.PaymentMethodDao;
import com.edu.wing.paymentMethod.domain.PaymentMethodVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {

  @Autowired
  private PaymentMethodDao paymentMethodDao;

  @Override
  public List<PaymentMethodVo> paymentMethodSelectList() {return paymentMethodDao.paymentMethodSelectList();}

  @Override
  public boolean paymentMethodExist(String paymentMethodName) {
    PaymentMethodVo paymentMethodVo = paymentMethodDao.paymentMethodExist(paymentMethodName);

    return paymentMethodVo != null;
  }

  @Override
  public boolean addPaymentMethod(String paymentMethodName) {
    // 1. insert 수행
    paymentMethodDao.insertPaymentMethod(paymentMethodName);

    // 2. 삽입 확인
    int paymentMethodNo = paymentMethodDao.selectPaymentMethodNoByName(paymentMethodName);

    return paymentMethodNo > 0;
  }

  @Override
  public int pmTotalCount(int paymentMethodNo) {
    return paymentMethodDao.pmTotalCount(paymentMethodNo);
  }

  @Override
  public PaymentMethodVo paymentMethodSelectOne(int paymentMethodNo) {
    return paymentMethodDao.paymentMethodSelectOne(paymentMethodNo);
  }

  @Override
  public boolean paymentMethodDeleteOne(int paymentMethodNo) {
    return  paymentMethodDao.paymentMethodDeleteOne(paymentMethodNo);
  }

  @Override
  public void paymentMethodUpdateOne(PaymentMethodVo paymentMethodVo) {
    // 3. 데이터베이스 업데이트 수행

   paymentMethodDao.paymentMethodUpdateOne(paymentMethodVo);

  }
}

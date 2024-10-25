package com.edu.wing.paymentMethod.dao;

import com.edu.wing.paymentMethod.domain.PaymentMethodVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentMethodDaoImpl implements PaymentMethodDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.paymentMethod.";

  @Override
  public List<PaymentMethodVo> paymentMethodSelectList() {
    return sqlSession.selectList(NAMESPACE + "paymentMethodSelectList");
  }

  @Override
  public PaymentMethodVo paymentMethodExist(String paymentMethodName) {
    return sqlSession.selectOne(NAMESPACE + "paymentMethodExist", paymentMethodName);
  }

  @Override
  public void insertPaymentMethod(String paymentMethodName) {
    sqlSession.insert(NAMESPACE + "insertPaymentMethod", paymentMethodName);
  }

  @Override
  public int selectPaymentMethodNoByName(String paymentMethodName) {
    return sqlSession.selectOne(NAMESPACE + "selectPaymentMethodNoByName", paymentMethodName);
  }

  @Override
  public int pmTotalCount(int paymentMethodNo) {
    return sqlSession.selectOne(NAMESPACE + "pmTotalCount", paymentMethodNo);
  }

  @Override
  public PaymentMethodVo paymentMethodSelectOne(int paymentMethodNo){
    return sqlSession.selectOne(NAMESPACE + "paymentMethodSelectOne", paymentMethodNo);
  }

  @Override
  public boolean paymentMethodDeleteOne(int paymentMethodNo) {
    int result = sqlSession.delete(NAMESPACE + "paymentMethodDeleteOne", paymentMethodNo);

    return result > 0;
  }

  @Override
  public PaymentMethodVo paymentMethodUpdateOne(PaymentMethodVo paymentMethodVo) {

    sqlSession.update(NAMESPACE + "paymentMethodUpdateOne", paymentMethodVo);

    return paymentMethodVo;
  }
}

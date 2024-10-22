package com.edu.wing.paymentMethod.dao;

import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.paymentMethod.domain.PaymentMethodVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
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
  public int countByPaymentMethodName(String paymentMethodName) {
    return sqlSession.selectOne(NAMESPACE + "countByPaymentMethodName", paymentMethodName);
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
    int result = sqlSession.update(NAMESPACE + "paymentMethodDeleteOne", paymentMethodNo);

    return result > 0;
  }
}

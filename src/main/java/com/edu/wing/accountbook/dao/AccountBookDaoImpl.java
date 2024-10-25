package com.edu.wing.accountbook.dao;

import com.edu.wing.accountbook.domain.AccountBookVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccountBookDaoImpl implements AccountBookDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.accountbook.";


  @Override
  public List<AccountBookVo> selectAccountBook() {
    return sqlSession.selectList(NAMESPACE+"selectAccountBook");
  }

  @Override
  public List<AccountBookVo> selectAccountBooks(Map<String, Object> params) {
    return sqlSession.selectList(NAMESPACE+"selectAccountBooks", params);
  }

  @Override
  public List<String> selectPlusCategories() {
    return sqlSession.selectList(NAMESPACE+"selectPlusCategories");
  }
  @Override
  public List<String> selectMinusCategories() {
    return sqlSession.selectList(NAMESPACE+"selectMinusCategories");
  }

  @Override
  public List<String> selectPaymentMethods() {
    return sqlSession.selectList(NAMESPACE+"selectPaymentMethods");
  }

  @Override
  public List<AccountBookVo> getTopPaymentMethods() {
    return sqlSession.selectList(NAMESPACE+"getTopPaymentMethods");
  }

  @Override
  public List<AccountBookVo> getTopCategories() {
    return sqlSession.selectList(NAMESPACE + "getTopCategories");
  }

  @Override
  public void accountBookDelete(int memberNo) {
    sqlSession.delete(NAMESPACE + "accountBookDelete", memberNo);
  }




  @Override
  public List<AccountBookVo> selectAccountBookByRecentDate(Map<String, Object> params) {
    return sqlSession.selectList(NAMESPACE + "selectAccountBookByRecentDate", params);
  }

  @Override
  public int selectMonthlyEntryCount(Map<String, Object> params) {
    return sqlSession.selectOne(NAMESPACE + "selectMonthlyEntryCount", params);
  }

  @Override
  public List<AccountBookVo> getMonthlyEntries(int memberNo, String startDate, String endDate) {
    // 매개변수를 Map으로 묶어서 전달
    Map<String, Object> params = Map.of(
        "memberNo", memberNo,
        "startDate", startDate,
        "endDate", endDate
    );
    return sqlSession.selectList(NAMESPACE + "getMonthlyEntries", params);
  }

  @Override
  public int insertAccountBook(Map<String, Object> params) {
    return sqlSession.insert(NAMESPACE + "insertAccountBook", params);
  }
  @Override
  public List<AccountBookVo> selectAccountBookByMonth(Date startDate, Date endDate, int memberNo, int limit) {
    Map<String, Object> params = new HashMap<>();
    params.put("startDate", startDate);
    params.put("endDate", endDate);
    params.put("memberNo", memberNo);
    params.put("limit", limit);
    return sqlSession.selectList(NAMESPACE+"selectAccountBookByMonth",params);
  }
  @Override
  public AccountBookVo selectAccountBookDetail(int accountBookNo, int memberNo) {
    return sqlSession.selectOne(NAMESPACE + "selectAccountBookDetail",
        Map.of("accountBookNo", accountBookNo, "memberNo", memberNo));
  }
}
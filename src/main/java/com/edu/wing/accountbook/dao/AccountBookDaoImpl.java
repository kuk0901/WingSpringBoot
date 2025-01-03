package com.edu.wing.accountbook.dao;

import com.edu.wing.accountbook.domain.AccountBookVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    public void deleteAllAccountBook(int memberNo) {
        sqlSession.delete(NAMESPACE + "deleteAllAccountBook", memberNo);
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
    public List<AccountBookVo> getMonthlyEntries(int memberNo, LocalDate startDate, LocalDate endDate) {
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


    @Override
    public void cardPurchaseOfAccountBook(AccountBookVo accountBookVo) {
      sqlSession.insert(NAMESPACE + "cardPurchaseOfAccountBook", accountBookVo);
    }

    @Override
    public AccountBookVo verifyTodayCardPurchaseAccountBookEntry(AccountBookVo accountBookVo) {
      return sqlSession.selectOne(NAMESPACE + "verifyTodayCardPurchaseAccountBookEntry", accountBookVo);
    }

    @Override
    public void deleteAccountBook(int accountBookNo) {
        sqlSession.delete(NAMESPACE + "deleteAccountBook", accountBookNo);
    }

    @Override
    public int updateAccountBook(Map<String, Object> params) {
        return sqlSession.update(NAMESPACE + "updateAccountBook", params);
    }

    @Override
    public List<AccountBookVo> selectMonthlyExpenseBook(int memberNo, Date startDate, Date endDate) {
        return sqlSession.selectList(NAMESPACE+"selectMonthlyExpenseBook",
                Map.of("memberNo", memberNo, "startDate", startDate, "endDate", endDate));
    }

    @Override
    public List<AccountBookVo> selectMonthlyIncomeBook(int memberNo, Date startDate, Date endDate) {
        return sqlSession.selectList(NAMESPACE+"selectMonthlyIncomeBook",
                Map.of("memberNo", memberNo, "startDate", startDate, "endDate", endDate));
    }

    @Override
    public int insertPayback(Map<String, Object> paybackData) {
        return sqlSession.insert("com.edu.wing.payback.insertPayback", paybackData);
    }

    @Override
    public List<AccountBookVo> getMonthlyPayback(Map<String, Object> params) {
        return sqlSession.selectList(NAMESPACE + "getMonthlyPayback", params);
    }

    @Override
    public List<AccountBookVo> getCardDetailForMypage(int memberNo, Integer categoryNo, LocalDate startDate) {
         Map<String, Object> params = new HashMap<>();
        params.put("memberNo", memberNo);
        params.put("categoryNo", categoryNo);
        params.put("startDate", startDate);
        return sqlSession.selectList(NAMESPACE+"getCardDetailForMypage",params);
    }

    @Override
    public int softDeleteAccountBook(int accountBookNo) {
        return sqlSession.update(NAMESPACE + "softDeleteAccountBook", accountBookNo);
    }

    @Override
    public int softAllDeleteAccountBook(int accountBookNo) {
        return sqlSession.update(NAMESPACE + "softAllDeleteAccountBook", accountBookNo);
    }

    @Override
    public int deleteAllPayBack(int memberNo) {
        return sqlSession.delete("com.edu.wing.payback.deleteAllPayBack", memberNo);
    }

    @Override
    public int updatePayback(Map<String, Object> params) {
        return sqlSession.update(NAMESPACE + "updatePayback", params);
    }


}

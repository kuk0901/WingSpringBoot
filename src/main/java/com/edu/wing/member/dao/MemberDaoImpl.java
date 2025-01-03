package com.edu.wing.member.dao;

import com.edu.wing.member.domain.MemberVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class MemberDaoImpl implements MemberDao {

  @Autowired
  private SqlSession sqlSession;

  private final String namespace = "com.edu.wing.member.";

  @Override
  public MemberVo memberExist(String email, String pwd) {
    HashMap<String, String> map = new HashMap<>();
    map.put("email", email);
    map.put("pwd", pwd);

    return sqlSession.selectOne(namespace + "memberExist", map);
  }

  @Override
  public MemberVo memberSelectOne(String email) {
    return sqlSession.selectOne(namespace + "memberSelectOne", email);
  }

  @Override
  public int memberInsertOne(MemberVo memberVo) {
    return sqlSession.insert(namespace + "memberInsertOne", memberVo);
  }

  @Override
  public MemberVo selectMemberByEmail(String email) {
    return sqlSession.selectOne(namespace + "selectMemberByEmail", email);
  }

  @Override
  public List<MemberVo> selectAllMembersForAdmin(int start, int end) {
    Map<String, Object> map = new HashMap<>();
    map.put("start", start);
    map.put("end", end);
    return sqlSession.selectList(namespace+"selectAllMembersForAdmin",map);
  }

  @Override
  public int selectTotalMembersCount() {
    return sqlSession.selectOne(namespace + "selectTotalMembersCount");
  }

  @Override
  public MemberVo selectMemberDetailForAdmin(int memberNo) {
    return sqlSession.selectOne(namespace + "selectMemberDetailForAdmin", memberNo);
  }

  @Override
  public void adminSoftDeleteMember(MemberVo memberVo) {
    sqlSession.update(namespace + "adminSoftDeleteMember", memberVo);
  }

  @Override
  public MemberVo selectAdminMypageInfo(int memberNo) {
    return sqlSession.selectOne(namespace + "selectAdminMypageInfo", memberNo);
  }
  @Override
  public int updateMember(MemberVo memberVo) {
    return sqlSession.update(namespace + "updateMember", memberVo);
  }

  @Override
  public MemberVo updateMemberSelect(int memberNo) {
    return sqlSession.selectOne(namespace + "updateMemberSelect", memberNo);
  }

  @Override
  public MemberVo selectMyPageInfo(int memberNo) {
    return sqlSession.selectOne(namespace + "selectMyPageInfo", memberNo);
  }

  @Override
  public int updateMemberInfo(MemberVo memberVo) {
    return sqlSession.update(namespace + "updateMemberInfo", memberVo);
  }

  @Override
  public MemberVo selectUpdatedMemberInfo(int memberNo) {
    return sqlSession.selectOne(namespace + "selectUpdatedMemberInfo", memberNo);
  }

  @Override
  public void updateMemberQuitApply(MemberVo memberVo) {
    sqlSession.update(namespace + "updateMemberQuitApply", memberVo);
  }

  @Override
  public void updateMemberCardPurchase(int memberNo) {
    sqlSession.update(namespace + "updateMemberCardPurchase", memberNo);
  }

  @Override
  public void updateMemberProductPurchase(int memberNo) {
    sqlSession.update(namespace + "updateMemberProductPurchase", memberNo);
  }

  @Override
  public MemberVo checkMemberProductPurchase(int memberNo) {
    return sqlSession.selectOne(namespace + "checkMemberProductPurchase", memberNo);
  }

  @Override
  public int getExpensePercentileByMonthlySalary(int memberNo) {
    return sqlSession.selectOne(namespace + "getExpensePercentileByMonthlySalary", memberNo);
  }

  @Override
  public int getExpensePercentileByYearlySalary(int memberNo) {
    return sqlSession.selectOne(namespace + "getExpensePercentileByYearlySalary", memberNo);
  }

  @Override
  public MemberVo findMemberAccount(Map<String, String> map) {
    return sqlSession.selectOne(namespace + "findMemberAccount", map);
  }

  @Override
  public MemberVo findMemberPassword(Map<String, String> map) {
    return sqlSession.selectOne(namespace + "findMemberPassword", map);
  }

  @Override
  public void updateMemberPassword(Map<String, String> map) {
    sqlSession.update(namespace + "updateMemberPassword", map);
  }

  @Override
  public MemberVo updateMemberPasswordCheck(Map<String, String> map) {
    return sqlSession.selectOne(namespace + "updateMemberPasswordCheck", map);
  }

  @Override
  public List<Integer> selectDeletedMemberNos() {
    return sqlSession.selectList(namespace + "selectDeletedMemberNos");
  }

}

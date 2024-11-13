package com.edu.wing.member.dao;

import com.edu.wing.member.domain.MemberVo;

import java.util.List;
import java.util.Map;

public interface MemberDao {
  MemberVo memberExist(String email, String pwd);
  MemberVo memberSelectOne(String email);
  int memberInsertOne(MemberVo memberVo);
  MemberVo selectMemberByEmail(String email);

  List<MemberVo> selectAllMembersForAdmin(int start, int end);
  int selectTotalMembersCount();
  MemberVo selectMemberDetailForAdmin(int memberNo);

  void adminSoftDeleteMember(MemberVo memberVo);

  MemberVo selectAdminMypageInfo(int memberNo);//관리자 마이페이지용
  int updateMember(MemberVo memberVo);//관리자마이페이지수정용
  MemberVo updateMemberSelect(int memberNo);
  MemberVo selectMyPageInfo(int memberNo); // 회원 정보마이페이지 조회 메서드
  int updateMemberInfo(MemberVo memberVo);
  MemberVo selectUpdatedMemberInfo(int memberNo);
  void updateMemberQuitApply(MemberVo memberVo);

  // 카드 신청
  void updateMemberCardPurchase(int memberNo);
  // 카드 해지
  void updateMemberProductPurchase(int memberNo);
  MemberVo checkMemberProductPurchase(int memberNo); // 신청, 해지 확인

  int getExpensePercentileByMonthlySalary(int memberNo);
  int getExpensePercentileByYearlySalary(int memberNo);

  MemberVo findMemberAccount(Map<String, String> map);
  MemberVo findMemberPassword(Map<String, String> map);

  void updateMemberPassword(Map<String, String> map);
  MemberVo updateMemberPasswordCheck(Map<String, String> map);

  List<Integer> selectDeletedMemberNos();

}

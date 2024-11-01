package com.edu.wing.member.dao;

import com.edu.wing.member.domain.MemberVo;

import java.util.List;

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

  MemberVo selectMyPageInfo(int memberNo); // 회원 정보마이페이지 조회 메서드
  int updateMemberInfo(MemberVo memberVo);
  void updateMemberQuitApply(MemberVo memberVo);
}

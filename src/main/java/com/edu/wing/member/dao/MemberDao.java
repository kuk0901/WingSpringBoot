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

  int adminDeleteMember(int memberNo); // 관리자 삭제 메소드

  MemberVo selectAdminMypageInfo(int memberNo);//관리자 마이페이지용
  int updateMember(MemberVo memberVo);//관리자마이페이지수정용
}

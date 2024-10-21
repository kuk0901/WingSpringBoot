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
}

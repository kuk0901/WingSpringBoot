package com.edu.wing.member.dao;

import com.edu.wing.member.domain.MemberVo;

public interface MemberDao {
  MemberVo memberExist(String email, String pwd);
  MemberVo memberSelectOne(String email);
  int memberInsertOne(MemberVo memberVo);
  MemberVo selectMemberByEmail(String email);
}

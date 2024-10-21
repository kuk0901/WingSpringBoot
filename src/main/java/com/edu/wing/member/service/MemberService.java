package com.edu.wing.member.service;

import com.edu.wing.member.domain.MemberVo;

import java.util.List;
import java.util.Map;

public interface MemberService {
  MemberVo memberExist(String email, String pwd);
  boolean memberInsertOne(MemberVo memberVo);

  // 이메일 중복 체크 메소드
  boolean isEmailAlreadyRegistered(String email);

  List<MemberVo> selectAllMembersForAdmin(int start, int end);
  int selectTotalMembersCount();
  Map<String, Object> selectMemberDetailForAdmin(int memberNo);
}

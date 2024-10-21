package com.edu.wing.member.service;

import com.edu.wing.member.domain.MemberVo;

public interface MemberService {
  MemberVo memberExist(String email, String pwd);
  boolean memberInsertOne(MemberVo memberVo);

  // 이메일 중복 체크 메소드
  boolean isEmailAlreadyRegistered(String email);
}

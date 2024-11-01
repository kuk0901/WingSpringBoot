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

  void adminSoftDeleteMember(MemberVo memberVo);// 관리자 회원삭제
  MemberVo getAdminMypageInfo(int memberNo);//관리자마이페이지
  int updateMember(MemberVo memberVo);//마이페이지업데이트

  MemberVo getMyPageInfo(int memberNo);  // 회원 정보 조회 메서드
  void updateMemberInfo(MemberVo memberVo);
  void updateMemberQuitApply(MemberVo memberVo);
}

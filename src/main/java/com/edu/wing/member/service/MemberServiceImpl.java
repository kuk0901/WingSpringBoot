package com.edu.wing.member.service;

import com.edu.wing.member.dao.MemberDao;
import com.edu.wing.member.domain.MemberVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

  @Autowired
  private MemberDao memberDao;

  @Override
  public MemberVo memberExist(String email, String pwd) {
    return memberDao.memberExist(email, pwd);
  }

  @Override
  public boolean memberInsertOne(MemberVo memberVo) {
    memberDao.memberInsertOne(memberVo);
    MemberVo result = memberDao.memberSelectOne(memberVo.getEmail());

    return result != null;
  }

  @Override
  public boolean isEmailAlreadyRegistered(String email) {
    MemberVo result = memberDao.selectMemberByEmail(email);
    return result != null;
  }
}

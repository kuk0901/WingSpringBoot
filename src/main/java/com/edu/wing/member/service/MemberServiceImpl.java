package com.edu.wing.member.service;

import com.edu.wing.member.dao.MemberDao;
import com.edu.wing.member.domain.MemberVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
  private static final Logger log
          = LoggerFactory.getLogger(MemberServiceImpl.class);

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

  @Override
  public List<MemberVo> selectAllMembersForAdmin(int start, int end) {
    return memberDao.selectAllMembersForAdmin(start,end);
  }

  @Override
  public int selectTotalMembersCount() {
    return memberDao.selectTotalMembersCount();
  }

  @Override
  public Map<String, Object> selectMemberDetailForAdmin(int memberNo) {
    Map<String, Object> resultMap = new HashMap<>();

    MemberVo memberVo
            =memberDao.selectMemberDetailForAdmin(memberNo);
    resultMap.put("memberVo", memberVo);

    return resultMap;
  }


  @Override
  public boolean adminDeleteMember(int memberNo) {
    int deletedCount = memberDao.adminDeleteMember(memberNo);
    return deletedCount > 0; // 삭제된 행 수가 0보다 크면 성공
  }

  @Override
  public MemberVo getAdminMypageInfo(int memberNo) {
    return memberDao.selectAdminMypageInfo(memberNo);
  }
  @Override
  public int updateMember(MemberVo memberVo) {
    return memberDao.updateMember(memberVo);
  }
}

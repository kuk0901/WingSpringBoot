package com.edu.wing.member.service;

import com.edu.wing.accountbook.dao.AccountBookDao;
import com.edu.wing.member.dao.MemberDao;
import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.sellingCard.dao.SellingCardDao;
import com.edu.wing.util.CustomEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl implements MemberService {
  private static final Logger log
          = LoggerFactory.getLogger(MemberServiceImpl.class);

  @Autowired
  private MemberDao memberDao;
  @Autowired
  private AccountBookDao accountBookDao;
  @Autowired
  private SellingCardDao sellingCardDao;

  @Override
  public MemberVo memberExist(String email, String pwd) {

    String encodedPassword = CustomEncoding.encode(pwd);
    log.info("member pwd: {}", pwd);
    log.info("member encodedPassword: {}", encodedPassword);

    return memberDao.memberExist(email, encodedPassword);
  }

  @Override
  public boolean memberInsertOne(MemberVo memberVo) {
    // 비밀번호 인코딩
    String encodedPassword = CustomEncoding.encode(memberVo.getPwd());
    memberVo.setPwd(encodedPassword); // 인코딩된 비밀번호를 설정

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

    memberVo.setPwd(CustomEncoding.decode(memberVo.getPwd()));

    resultMap.put("memberVo", memberVo);

    return resultMap;
  }

  @Override
  public void adminSoftDeleteMember(MemberVo memberVo) {
    memberDao.adminSoftDeleteMember(memberVo);
  }

  @Override
  public MemberVo getAdminMypageInfo(int memberNo) {
    MemberVo memberVo = memberDao.selectAdminMypageInfo(memberNo);

    memberVo.setPwd(CustomEncoding.decode(memberVo.getPwd()));

    return memberVo;
  }

  @Override
  @Transactional
  public MemberVo updateMember(MemberVo memberVo) {
    // 비밀번호 인코딩
    memberVo.setPwd(CustomEncoding.encode(memberVo.getPwd()));

    // 업데이트
    memberDao.updateMember(memberVo);

    // 업데이트 후 회원 정보 조회
    MemberVo updatedMemberVo = memberDao.updateMemberSelect(memberVo.getMemberNo());

    // 두 객체 비교 (equals를 오버라이딩하거나, 특정 필드만 비교)
    if (memberVo.equals(updatedMemberVo)) {
      System.out.println("업데이트가 반영되지 않았습니다.");
    }

    return memberDao.memberExist(updatedMemberVo.getEmail(), updatedMemberVo.getPwd());
  }

  @Override
  public MemberVo getMyPageInfo(int memberNo) {
    MemberVo memberVo = memberDao.selectMyPageInfo(memberNo);

    memberVo.setPwd(CustomEncoding.decode(memberVo.getPwd()));

    return memberVo;
  }

  @Override
  public MemberVo updateMemberInfo(MemberVo memberVo) {
    // 업데이트 전 회원 정보 조회
    MemberVo originalMemberVo = memberDao.selectUpdatedMemberInfo(memberVo.getMemberNo());
    // 비밀번호 인코딩
    memberVo.setPwd(CustomEncoding.encode(memberVo.getPwd()));
    // 업데이트
    memberDao.updateMemberInfo(memberVo);
    // 업데이트 후 회원 정보 조회
    MemberVo updatedMemberVo = memberDao.selectUpdatedMemberInfo(memberVo.getMemberNo());
    // 두 객체 비교 (equals를 오버라이딩하거나, 특정 필드만 비교)
    if (originalMemberVo.equals(updatedMemberVo)) {
      System.out.println("업데이트가 반영되지 않았습니다.");
    }
    return updatedMemberVo;
  }

  @Override
  public void updateMemberQuitApply(MemberVo memberVo) {
    memberDao.updateMemberQuitApply(memberVo);
  }

  @Override
  public int getExpensePercentileByMonthlySalary(int memberNo) {
    return memberDao.getExpensePercentileByMonthlySalary(memberNo);
  }

  @Override
  public int getExpensePercentileByYearlySalary(int memberNo) {
    return memberDao.getExpensePercentileByYearlySalary(memberNo);
  }

  @Override
  public MemberVo findMemberAccount(Map<String, String> map) {
    String encodedPassword = CustomEncoding.encode(map.get("pwd"));
    map.put("pwd", encodedPassword); // 인코딩된 비밀번호를 설정

    return memberDao.findMemberAccount(map);
  }

  @Override
  public MemberVo findMemberPassword(Map<String, String> map) {
    return memberDao.findMemberPassword(map);
  }

  @Override
  @Transactional
  public boolean changeMemberPasswordAndValidate(Map<String, String> map) {
    String encodedPassword = CustomEncoding.encode(map.get("pwd"));
    map.put("pwd", encodedPassword); // 인코딩된 비밀번호를 설정

    memberDao.updateMemberPassword(map);

    MemberVo memberVo = memberDao.updateMemberPasswordCheck(map);

    return memberVo.getUserName().equals(map.get("userName"))
        && memberVo.getEmail().equals(map.get("email"))
        && memberVo.getPwd().equals(map.get("pwd"));
  }

  @Override
  public List<Integer> selectDeletedMemberNos() {
    return memberDao.selectDeletedMemberNos();
  }

  // 매달 실행될 삭제 로직
  @Transactional
  @Override
  public void deleteInactiveMembers() {
    List<Integer> deletedMemberNos = memberDao.selectDeletedMemberNos();

    for (int memberNo : deletedMemberNos) {
      accountBookDao.deleteAllPayBack(memberNo);
      accountBookDao.deleteAllAccountBook(memberNo);
      sellingCardDao.updateDeleteCard(memberNo);
    }
  }

}

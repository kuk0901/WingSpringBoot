package com.edu.wing.util;

import java.util.ArrayList;
import java.util.Random;

public class RandomAlertMessage {
  private final Random random = new Random();
  private final ArrayList<String> memberLoginAlertMsg = new ArrayList<>();
  private final ArrayList<String> adminLoginAlertMsg = new ArrayList<>();
  private final ArrayList<String>  memberLogoutAlertMsg = new ArrayList<>();
  private final ArrayList<String>  adminLogoutAlertMsg = new ArrayList<>();

  public RandomAlertMessage() {
    initializeMessages();
  }

  private void initializeMessages() {
    memberLoginAlertMsg.add("회원님, 오늘도 좋은 하루 되세요!");
    memberLoginAlertMsg.add("슬기로운 소비 생활 시작!");
    memberLoginAlertMsg.add("가계부 작성을 시작해 보세요.");
    memberLoginAlertMsg.add("WING_ 가계부 사이트를 방문해 주셔서 감사합니다.");
    memberLoginAlertMsg.add("최근 소비를 기록해 보세요.");
    memberLoginAlertMsg.add("회원님, 환영합니다!");

    adminLoginAlertMsg.add("관리자님, 환영합니다!");
    adminLoginAlertMsg.add("오늘의 새로운 데이터를 확인해 보세요!");
    adminLoginAlertMsg.add("관리자님, 새로운 카드를 등록해 보는 건 어떠신가요?");
    adminLoginAlertMsg.add("관리자님, 오늘도 좋은 하루 되세요!");
    adminLoginAlertMsg.add("항상 사이트를 관리해 주셔서 감사합니다.");

    memberLogoutAlertMsg.add("로그아웃 되었습니다.");
    memberLogoutAlertMsg.add("회원님, 오늘의 소비 기록은 만족스러우셨나요?");
    memberLogoutAlertMsg.add("WING_ 가계부 사이트를 이용해 주셔서 감사합니다.");
    memberLogoutAlertMsg.add("내일 또 뵙겠습니다!");

    adminLogoutAlertMsg.add("로그아웃 되었습니다.");
    adminLogoutAlertMsg.add("오늘도 WING_ 가계부 사이트를 관리해 주셔서 감사합니다.");
    adminLogoutAlertMsg.add("관리자님, 오늘의 데이터는 마음에 드셨나요?");
    adminLogoutAlertMsg.add("내일 또 뵙겠습니다!");
  }

  public String getRandomMemberLoginAlert() {
    return memberLoginAlertMsg.get(random.nextInt(memberLoginAlertMsg.size()));
  }

  public String getRandomAdminLoginAlert() {
    return adminLoginAlertMsg.get(random.nextInt(adminLoginAlertMsg.size()));
  }

  public String getRandomAdminLogoutAlert() {
    return adminLogoutAlertMsg.get(random.nextInt(adminLogoutAlertMsg.size()));
  }

  public String getRandomMemberLogoutAlert() {
    return memberLogoutAlertMsg.get(random.nextInt(memberLogoutAlertMsg.size()));
  }

}

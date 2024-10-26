package com.edu.wing.util;

import java.util.ArrayList;
import java.util.Random;

public class RandomAlertMessage {
  private final Random random = new Random();
  private final ArrayList<String>  memberAlertMsg = new ArrayList<>();
  private final ArrayList<String>  adminAlertMsg = new ArrayList<>();

  public RandomAlertMessage() {
    initializeMessages();
  }

  private void initializeMessages() {
    memberAlertMsg.add("회원님, 오늘도 좋은 하루 되세요!");
    memberAlertMsg.add("슬기로운 소비 생활 시작!");
    memberAlertMsg.add("가계부 작성을 시작해 보세요.");
    memberAlertMsg.add("WING_ 가계부 사이트를 방문해 주셔서 감사합니다.");
    memberAlertMsg.add("최근 소비를 기록해 보세요.");
    memberAlertMsg.add("회원님, 환영합니다!");

    adminAlertMsg.add("관리자님, 환영합니다!");
    adminAlertMsg.add("오늘의 새로운 데이터를 확인해 보세요!");
    adminAlertMsg.add("관리자님, 새로운 카드를 등록해 보는 건 어떠신가요?");
    adminAlertMsg.add("관리자님, 오늘도 좋은 하루 되세요!");
    adminAlertMsg.add("항상 사이트를 관리해 주셔서 감사합니다.");
  }

  public String getRandomMemberAlert() {
    return memberAlertMsg.get(random.nextInt(memberAlertMsg.size()));
  }

  public String getRandomAdminAlert() {
    return adminAlertMsg.get(random.nextInt(adminAlertMsg.size()));
  }
}

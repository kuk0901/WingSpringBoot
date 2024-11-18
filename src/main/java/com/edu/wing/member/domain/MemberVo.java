package com.edu.wing.member.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class MemberVo implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;  // 버전 관리를 위한 ID

  @Id
  private int memberNo;                 // 회원번호 (기본키)
  private String email;                // 이메일
  private String pwd;             // 비밀번호
  private String userName;           // 성명
  private String phone;                // 휴대폰 번호
  private String grade;                // 등급
  private int yearlySalary;            // 연봉
  private int monthlySalary;           // 월급
  private Date creDate;          // 가입일자
  private Date modDate;         // 수정일자
  private String quitApply;            // 탈퇴신청 여부
  private String productPurchase;      // 상품구매 여부
  private String isDeleted; //탈퇴여부
  // 추가된 필드
  private Date sellingDate;       // 카드 신청 날짜
  private Date terminationDate;   // 카드 해지 날짜
  private String cardName;        // 구매 카드 이름
  private String terminationReason; // 해지 사유
  private int accountBookCount;   // 가계부 작성 수
  private int postCount;          // 게시글 작성 수
  private String memberCardNo;      //보유 카드번호
  private  int freeBoardCount;
}
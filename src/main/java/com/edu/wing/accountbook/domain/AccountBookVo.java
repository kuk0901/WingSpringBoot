package com.edu.wing.accountbook.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class AccountBookVo {
  @Id
  private int  accountBookNo;
  private Long  plusCategoryNo;
  private Long  minusCategoryNo;
  private int  memberNo;
  private Long  paymentMethodNo;
  private Date creDate;
  private String content;
  private String paymentAmount;
  //나중에 삭제예정?

  // 카테고리 이름 및 결제 방법 이름
  private String minusCategoryName;    // MINUS_CATEGORY의 CATEGORY_NAME
  private String plusCategoryName;     // PLUS_CATEGORY의 CATEGORY_NAME
  private String paymentMethodName;    // PAYMENT_METHOD의 PAYMENT_METHOD_NAME
  private String memberEmail;        // MEMBER 테이블의 EMAIL


  // 추가: 사용 횟수 필드
  private Long  usageCount;
}

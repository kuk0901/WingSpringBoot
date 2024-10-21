package com.edu.wing.accountbook.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class AccountBookVo {
  @Id
  private int accountBookNo;
  private int plusCategoryNo;
  private int minusCategoryNo;
  private int memberNo;
  private int paymentMethodNo;
  private Date creDate;
  private String content;
  private String paymentAmount;
  //나중에 삭제예정
  private String minusCategoryName; // MINUS_CATEGORY의 CATEGORY_NAME
  private String memberEmail;        // MEMBER 테이블의 EMAIL
  private String paymentMethodName;  // PAYMENT_METHOD의 PAYMENT_METHOD_NAME
}

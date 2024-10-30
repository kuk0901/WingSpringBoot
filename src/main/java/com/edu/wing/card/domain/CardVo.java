package com.edu.wing.card.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class CardVo {
  @Id
  private int cardNo;

  private String cardName;
  private String cardCompany;
  private int categoryNo;
  private String storedFileName;
  private String originalFileName;
  private String summationBenefit;
  private String mainBenefit;
  private Date registerDate;
  private String isDeleted;

  private String categoryName;
}

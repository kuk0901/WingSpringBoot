package com.edu.wing.cardBenefit.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CardBenefitVo {

  @Id
  private int cardBenefitNo;
  private int cardNo;
  private String cardBenefitDivision;
  private String cardBenefitDetail;
  private int cardPercentage;
}

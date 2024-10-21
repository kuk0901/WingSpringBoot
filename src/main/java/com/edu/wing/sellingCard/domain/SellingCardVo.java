package com.edu.wing.sellingCard.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class SellingCardVo {
  @Id
  private int sellingCardNo;

  private int cardNo;
  private int memberNo;
  private String memberCardNo;
  private String cardTermination;
  private String terminationReason;
  private Date sellingDate;

  private String cardRecommend;
  private String email;
}

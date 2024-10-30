package com.edu.wing.inquiry.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class InquiryVo {

  @Id
  private int inquiryNo;
  private int noticeBoardNo;
  private int memberNo;
  private String title;
  private String content;
  private String division;
  private Date creDate;
  private String answerTermination;

}

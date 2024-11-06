package com.edu.wing.inquiryComment.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class InquiryCommentVo {

  @Id
  private int inquiryCommentNo;
  private int inquiryNo;
  private int memberNo;
  private String content;
  private Date creDate;
  private Date modDate;
}

package com.edu.wing.freeBoardComment.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class FreeBoardCommentVo {

  @Id
  private int freeBoardCommentNo;
  private int freeBoardNo;
  private int memberNo;
  private String content;
  private Date creDate;
  private Date modDate;
  private String email;
  private int isModified;
  private Date compareDate;
}

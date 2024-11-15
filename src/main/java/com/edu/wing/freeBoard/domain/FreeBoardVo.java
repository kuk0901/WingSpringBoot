package com.edu.wing.freeBoard.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class FreeBoardVo {

  @Id
  private int freeBoardNo;
  private int noticeBoardNo;
  private int memberNo;
  private String title;
  private String content;
  private Date creDate;
  private Date modDate;
  private String email;
  private Date compareDate;
  private int isModified;
}

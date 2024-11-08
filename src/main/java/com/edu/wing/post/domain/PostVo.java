package com.edu.wing.post.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class PostVo {

  @Id
  private int postNo;
  private int noticeBoardNo;
  private int memberNo;
  private String title;
  private String content;
  private Date creDate;
  private Date modDate;
  private String email;
}

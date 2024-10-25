package com.edu.wing.category.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class MinusCategoryVo {

  @Id
  private int categoryNo;
  private String categoryName;
}
package com.edu.wing.category.domain;


import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class MinusCategoryVo implements CategoryVo {

  @Id
  private int categoryNo;
  private String categoryName;

  public int getCategoryNo() {
    return categoryNo;
  }

  public String getCategoryName() {
    return categoryName;
  }
}

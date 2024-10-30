package com.edu.wing.inquiry.dao;

import com.edu.wing.inquiry.domain.InquiryVo;

import java.util.List;
import java.util.Map;

public interface InquiryDao {

  List<InquiryVo> inquirySelectList(int start, int end);

  int inquirySelectTotalCount();

  Map<String, Object> inquirySelectOne(int inquiryNo);

  int insertInquiryComment(int inquiryNo, String content);

  int updateInquiryComment(int commentNo, String content);

}

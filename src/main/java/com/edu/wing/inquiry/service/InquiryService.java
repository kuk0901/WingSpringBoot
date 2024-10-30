package com.edu.wing.inquiry.service;

import com.edu.wing.inquiry.domain.InquiryVo;

import java.util.List;
import java.util.Map;

public interface InquiryService {

  List<InquiryVo> inquirySelectList(int start, int end);

  int inquirySelectTotalCount();

  Map<String, Object> inquirySelectOne(int inquiryNo);

  boolean updateInquiryComment(int inquiryNo, String content);
}

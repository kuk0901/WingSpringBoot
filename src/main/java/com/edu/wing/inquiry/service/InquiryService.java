package com.edu.wing.inquiry.service;

import com.edu.wing.inquiry.domain.InquiryVo;

import java.util.List;
import java.util.Map;

public interface InquiryService {

  List<InquiryVo> inquirySelectList(int start, int end, String inquirySearch);

  int inquirySelectTotalCount(String inquirySearch);

  Map<String, Object> inquirySelectOne(int inquiryNo);

  boolean addInquiry(InquiryVo inquiryVo);

  boolean addInquiryReply(int inquiryNo, String content, int memberNo);

  boolean updateAnswerTermination(int inquiryNo);

  List<InquiryVo> memberInquirySelectList(int start, int end, int memberNo);

  Map<String, Object> memberInquirySelectOne(int inquiryNo);

}

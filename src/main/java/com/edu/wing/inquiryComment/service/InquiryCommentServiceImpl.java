package com.edu.wing.inquiryComment.service;


import com.edu.wing.inquiryComment.dao.InquiryCommentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
public class InquiryCommentServiceImpl implements InquiryCommentService{

  @Autowired
  private InquiryCommentDao inquiryCommentDao;

  @Override
  public boolean updateInquiryComment(int inquiryCommentNo, String content) {

    return inquiryCommentDao.updateInquiryComment(inquiryCommentNo, content) > 0;

  }

}

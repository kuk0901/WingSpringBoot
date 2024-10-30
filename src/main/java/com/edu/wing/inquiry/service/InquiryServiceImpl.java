package com.edu.wing.inquiry.service;

import com.edu.wing.inquiry.dao.InquiryDao;
import com.edu.wing.inquiry.domain.InquiryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class InquiryServiceImpl implements InquiryService {

  @Autowired
  private InquiryDao inquiryDao;

  @Override
  public List<InquiryVo> inquirySelectList(int start, int end) {
    return inquiryDao.inquirySelectList(start, end);
  }

  @Override
  public int inquirySelectTotalCount() {
    return inquiryDao.inquirySelectTotalCount();
  }

  @Override
  public Map<String, Object> inquirySelectOne(int inquiryNo) {
    return inquiryDao.inquirySelectOne(inquiryNo);
  }

  @Override
  public boolean updateInquiryComment(int inquiryNo, String content) {
    Map<String, Object> inquiry = inquiryDao.inquirySelectOne(inquiryNo);

    if (inquiry == null) {
      return false;
    }

    // BigDecimal을 Integer로 안전하게 변환
    Integer commentNo = inquiry.get("INQUIRYCOMMENTNO") != null
        ? ((BigDecimal) inquiry.get("INQUIRYCOMMENTNO")).intValue()
        : null;

    if (commentNo == null) {
      // 답변이 없는 경우, 새로운 답변을 추가
      return inquiryDao.insertInquiryComment(inquiryNo, content) > 0;
    } else {
      // 기존 답변이 있는 경우, 답변을 수정
      return inquiryDao.updateInquiryComment(commentNo, content) > 0;
    }
  }


}

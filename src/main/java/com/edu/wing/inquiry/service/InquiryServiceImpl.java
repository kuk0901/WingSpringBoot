package com.edu.wing.inquiry.service;

import com.edu.wing.inquiry.dao.InquiryDao;
import com.edu.wing.inquiry.domain.InquiryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InquiryServiceImpl implements InquiryService {

  @Autowired
  private InquiryDao inquiryDao;

  @Override
  public List<InquiryVo> inquirySelectList(int start, int end, String inquirySearch) {

    Map<String, Object> map = new HashMap<>();
    map.put("start", start);
    map.put("end", end);
    map.put("inquirySearch", inquirySearch);

    return inquiryDao.inquirySelectList(map);
  }

  @Override
  public int inquirySelectTotalCount(String inquirySearch) {
    return inquiryDao.inquirySelectTotalCount(inquirySearch);
  }

  @Override
  public Map<String, Object> inquirySelectOne(int inquiryNo) {
    return inquiryDao.inquirySelectOne(inquiryNo);
  }

  @Override
  public boolean addInquiry(InquiryVo inquiryVo) {
        int result = inquiryDao.addInquiry(inquiryVo);

        return result > 0;
  }

  @Override
  public boolean addInquiryReply(int inquiryNo, String content, int memberNo) {
    boolean added = inquiryDao.insertInquiryComment(inquiryNo, content, memberNo) > 0;
    if (added) {
      // 답글이 추가되면 ANSWER_TERMINATION을 업데이트합니다.
      updateAnswerTermination(inquiryNo);
    }
    return added;
  }

  @Override
  public boolean updateAnswerTermination(int inquiryNo) {
    return inquiryDao.updateAnswerTermination(inquiryNo) > 0;
  }

  @Override
  public List<InquiryVo> memberInquirySelectList(int start, int end, int memberNo) {
    Map<String, Object> map = new HashMap<>();

    map.put("start", start);
    map.put("end", end);
    map.put("memberNo", memberNo);

    return inquiryDao.memberInquirySelectList(map);
  }

  @Override
  public Map<String, Object> memberInquirySelectOne(int inquiryNo) {
    return inquiryDao.memberInquirySelectOne(inquiryNo);
  }

}

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
  public boolean updateInquiryComment(int inquiryNo, String content) {
    Map<String, Object> inquiry = inquiryDao.inquirySelectOne(inquiryNo);

    if (inquiry == null) {
      return false; // 문의가 존재하지 않으면 false 반환
    }

    // BigDecimal을 Integer로 안전하게 변환
    Integer commentNo = inquiry.get("INQUIRYCOMMENTNO") != null
        ? ((BigDecimal) inquiry.get("INQUIRYCOMMENTNO")).intValue()
        : null;

    if (commentNo != null) {
      // 기존 답변이 있는 경우, 답변을 수정
      return inquiryDao.updateInquiryComment(commentNo, content) > 0;
    } else {
      // 답변이 없는 경우 false 반환
      return false;
    }
  }

  @Override
  public void addInquiry(InquiryVo inquiryVo) {
    inquiryDao.addInquiry(inquiryVo);
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

}

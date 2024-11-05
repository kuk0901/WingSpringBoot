package com.edu.wing.inquiry.dao;

import com.edu.wing.inquiry.domain.InquiryVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InquiryDaoImpl implements InquiryDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.inquiry.";

  @Override
  public List<InquiryVo> inquirySelectList(Map<String, Object> map) {
    return sqlSession.selectList(NAMESPACE + "inquirySelectList", map);
  }

  @Override
  public int inquirySelectTotalCount(String inquirySearch) {
    return sqlSession.selectOne(NAMESPACE + "inquirySelectTotalCount", inquirySearch);
  }

  @Override
  public Map<String, Object> inquirySelectOne(int inquiryNo) {
    return sqlSession.selectOne(NAMESPACE + "inquirySelectOne", inquiryNo);
  }

  @Override
  public int insertInquiryComment(int inquiryNo, String content, int memberNo) {
    Map<String, Object> params = new HashMap<>();
    params.put("inquiryNo", inquiryNo);
    params.put("content", content);
    params.put("memberNo", memberNo);
    return sqlSession.insert(NAMESPACE + "insertInquiryComment", params);
  }

  @Override
  public int updateInquiryComment(int commentNo, String content) {
    Map<String, Object> params = new HashMap<>();
    params.put("commentNo", commentNo);
    params.put("content", content);
    return sqlSession.update(NAMESPACE + "updateInquiryComment", params);
  }

  @Override
  public void addInquiry(InquiryVo inquiryVo) {
    sqlSession.insert(NAMESPACE + "addInquiry", inquiryVo);
  }

  @Override
  public int updateAnswerTermination(int inquiryNo) {
    return sqlSession.update(NAMESPACE + "updateAnswerTermination", inquiryNo);
  }

  @Override
  public List<InquiryVo> memberInquirySelectList(Map<String, Object> map) {
    return sqlSession.selectList(NAMESPACE + "memberInquirySelectList", map);
  }
}

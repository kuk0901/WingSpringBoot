package com.edu.wing.inquiryComment.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InquiryCommentDaoImpl implements InquiryCommentDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.inquiryComment.";

  @Override
  public int updateInquiryComment(int inquiryCommentNo, String content) {
    Map<String, Object> params = new HashMap<>();
    params.put("inquiryCommentNo", inquiryCommentNo);
    params.put("content", content);
    return sqlSession.update(NAMESPACE + "updateInquiryComment", params);
  }

}

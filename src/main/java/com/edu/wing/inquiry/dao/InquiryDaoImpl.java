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
  public List<InquiryVo> inquirySelectList(int start, int end) {
    Map<String, Object> map = new HashMap<>();
    map.put("start", start);
    map.put("end", end);

    return sqlSession.selectList(NAMESPACE + "inquirySelectList", map);
  }

  @Override
  public int inquirySelectTotalCount() {
    return sqlSession.selectOne(NAMESPACE + "inquirySelectTotalCount");
  }

  @Override
  public Map<String, Object> inquirySelectOne(int inquiryNo) {
    return sqlSession.selectOne(NAMESPACE + "inquirySelectOne", inquiryNo);
  }

  @Override
  public int insertInquiryComment(int inquiryNo, String content) {
    Map<String, Object> params = new HashMap<>();
    params.put("inquiryNo", inquiryNo);
    params.put("content", content);
    // 현재 로그인한 관리자의 MEMBER_NO를 여기에 추가해야 합니다.
    // params.put("memberNo", getCurrentAdminMemberNo());
    return sqlSession.insert(NAMESPACE + "insertInquiryComment", params);
  }

  @Override
  public int updateInquiryComment(int commentNo, String content) {
    Map<String, Object> params = new HashMap<>();
    params.put("commentNo", commentNo);
    params.put("content", content);
    return sqlSession.update(NAMESPACE + "updateInquiryComment", params);
  }


}

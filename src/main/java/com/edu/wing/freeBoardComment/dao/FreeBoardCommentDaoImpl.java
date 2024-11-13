package com.edu.wing.freeBoardComment.dao;

import com.edu.wing.freeBoard.domain.FreeBoardVo;
import com.edu.wing.freeBoardComment.domain.FreeBoardCommentVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FreeBoardCommentDaoImpl implements FreeBoardCommentDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.freeBoardComment";

  @Override
  public List<FreeBoardCommentVo> freeBoardCommentSelectList(int freeBoardNo) {
    return sqlSession.selectList(NAMESPACE + ".freeBoardCommentSelectList", freeBoardNo);
  }

  @Override
  public int addComment(FreeBoardCommentVo freeBoardCommentVo) {
    return sqlSession.insert(NAMESPACE + ".addComment", freeBoardCommentVo);
  }

}

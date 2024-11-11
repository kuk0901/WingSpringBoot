package com.edu.wing.freeBoard.dao;

import com.edu.wing.freeBoard.domain.FreeBoardVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class FreeBoardDaoImpl implements FreeBoardDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.freeBoard.";

  @Override
  public int freeBoardSelectTotalCount(int noticeBoardNo, String freeBoardSearch) {
    Map<String, Object> resultMap = new HashMap<>();

    resultMap.put("noticeBoardNo", noticeBoardNo);
    resultMap.put("freeBoardSearch", freeBoardSearch);

    return sqlSession.selectOne(NAMESPACE + "freeBoardSelectTotalCount", resultMap);
  }

  @Override
  public List<FreeBoardVo> freeBoardSelectList(Map<String, Object> map) {
    return sqlSession.selectList(NAMESPACE + "freeBoardSelectList", map);
  }

  @Override
  public FreeBoardVo freeBoardSelectOne(int freeBoardNo) {
    return sqlSession.selectOne(NAMESPACE + "freeBoardSelectOne", freeBoardNo);
  }

  @Override
  public int addFreeBoard(FreeBoardVo freeBoardVo) {
    return sqlSession.insert(NAMESPACE + "addFreeBoard", freeBoardVo);
  }

  @Override
  public int updateFreeBoard(int freeBoardNo, String title, String content) {
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("freeBoardNo", freeBoardNo);
    resultMap.put("title", title);
    resultMap.put("content", content);

    return sqlSession.update(NAMESPACE + "updateFreeBoard", resultMap);
  }
}

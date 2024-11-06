package com.edu.wing.post.dao;

import com.edu.wing.post.domain.PostVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class PostDaoImpl implements PostDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.post.";

  @Override
  public int postSelectTotalCount(int noticeBoardNo) {
    return sqlSession.selectOne(NAMESPACE + "postSelectTotalCount", noticeBoardNo);
  }

  @Override
  public List<PostVo> postSelectList(Map<String, Object> map) {
    return sqlSession.selectList(NAMESPACE + "postSelectList", map);
  }

  @Override
  public void addPost(PostVo postVo) {
    sqlSession.insert(NAMESPACE + "addPost", postVo);
  }
}

package com.edu.wing.post.dao;

import com.edu.wing.post.domain.PostVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostDaoImpl implements PostDao {

  @Autowired
  private SqlSession sqlSession;

  private static final String NAMESPACE = "com.edu.wing.post.";

  @Override
  public int postSelectTotalCount(int noticeBoardNo, String postSearch) {
    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("noticeBoardNo", noticeBoardNo);
    resultMap.put("postSearch", postSearch);

    return sqlSession.selectOne(NAMESPACE + "postSelectTotalCount", resultMap);
  }

  @Override
  public List<PostVo> postSelectList(Map<String, Object> map) {
    return sqlSession.selectList(NAMESPACE + "postSelectList", map);
  }

  @Override
  public void addPost(PostVo postVo) {
    sqlSession.insert(NAMESPACE + "addPost", postVo);
  }

  @Override
  public PostVo postSelectOne(int postNo){
    return sqlSession.selectOne(NAMESPACE + "postSelectOne", postNo);
  }
}

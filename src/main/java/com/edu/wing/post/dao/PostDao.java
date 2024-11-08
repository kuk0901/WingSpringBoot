package com.edu.wing.post.dao;

import com.edu.wing.post.domain.PostVo;

import java.util.List;
import java.util.Map;

public interface PostDao {

  int postSelectTotalCount(int noticeBoardNo, String postSearch);

  List<PostVo> postSelectList(Map<String, Object> map);

  void addPost(PostVo postVo);

  Map<String, Object> postSelectOne(int postNo);

  int updatePost(int postNo, String title, String content, int memberNo);

  boolean deletePost(int postNo);

}

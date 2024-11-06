package com.edu.wing.post.dao;

import com.edu.wing.post.domain.PostVo;

import java.util.List;
import java.util.Map;

public interface PostDao {

  int postSelectTotalCount(int noticeBoardNo);

  List<PostVo> postSelectList(Map<String, Object> map);

  void addPost(PostVo postVo);

}

package com.edu.wing.post.service;


import com.edu.wing.post.domain.PostVo;

import java.util.List;
import java.util.Map;

public interface PostService {

  int postSelectTotalCount(int noticeBoardNo, String postSearch);

  List<PostVo> postSelectList(int start, int end, String postSearch, int noticeBoardNo);

  void addPost(PostVo postVo);

  Map<String, Object> postSelectOne(int postNo);

  boolean updatePost(int postNo, String title, String content, int memberNo);

  boolean deletePost(int postNo);
}

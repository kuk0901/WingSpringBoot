package com.edu.wing.post.service;


import com.edu.wing.post.domain.PostVo;

import java.util.List;

public interface PostService {

  int postSelectTotalCount(int noticeBoardNo);

  List<PostVo> postSelectList(int start, int end, String postSearch, int noticeBoardNo);

}

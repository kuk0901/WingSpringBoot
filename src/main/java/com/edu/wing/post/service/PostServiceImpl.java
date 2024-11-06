package com.edu.wing.post.service;


import com.edu.wing.post.dao.PostDao;
import com.edu.wing.post.domain.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService{

  @Autowired
  private PostDao postDao;

  @Override
  public int postSelectTotalCount(int noticeBoardNo) {

    return postDao.postSelectTotalCount(noticeBoardNo);
  }

  @Override
  public List<PostVo> postSelectList(int start, int end, String postSearch, int noticeBoardNo) {

    Map<String, Object> map = new HashMap<>();
    map.put("start", start);
    map.put("end", end);
    map.put("postSearch", postSearch);
    map.put("noticeBoardNo", noticeBoardNo);

    return postDao.postSelectList(map);
  }

}

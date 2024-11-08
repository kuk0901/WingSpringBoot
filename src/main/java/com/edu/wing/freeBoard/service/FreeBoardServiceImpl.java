package com.edu.wing.freeBoard.service;

import com.edu.wing.freeBoard.dao.FreeBoardDao;
import com.edu.wing.freeBoard.domain.FreeBoardVo;
import com.edu.wing.post.domain.PostVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {

  @Autowired
  private FreeBoardDao freeBoardDao;

  @Override
  public int freeBoardSelectTotalCount(int noticeBoardNo, String freeBoardSearch) {

    return freeBoardDao.freeBoardSelectTotalCount(noticeBoardNo, freeBoardSearch);
  }

  @Override
  public List<FreeBoardVo> freeBoardSelectList(int start, int end, String freeBoardSearch, int noticeBoardNo) {

    Map<String, Object> map = new HashMap<>();
    map.put("start", start);
    map.put("end", end);
    map.put("postSearch", freeBoardSearch);
    map.put("noticeBoardNo", noticeBoardNo);

    return freeBoardDao.freeBoardSelectList(map);
  }

  @Override
  public FreeBoardVo freeBoardSelectOne(int freeBoardNo) {
    return freeBoardDao.freeBoardSelectOne(freeBoardNo);
  }
}

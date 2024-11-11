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

  @Override
  public boolean addFreeBoard(String title, String content, String email, int noticeBoardNo, int memberNo) {
    FreeBoardVo freeBoardVo = new FreeBoardVo();
    freeBoardVo.setTitle(title);
    freeBoardVo.setContent(content);
    freeBoardVo.setEmail(email);
    freeBoardVo.setNoticeBoardNo(noticeBoardNo);
    freeBoardVo.setMemberNo(memberNo);

    int result = freeBoardDao.addFreeBoard(freeBoardVo);
    return result > 0;
  }

  @Override
  public boolean updateFreeBoard(int freeBoardNo, String title, String content) {
    return freeBoardDao.updateFreeBoard(freeBoardNo, title, content) > 0;
  }
}

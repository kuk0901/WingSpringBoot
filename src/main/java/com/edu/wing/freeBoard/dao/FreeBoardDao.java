package com.edu.wing.freeBoard.dao;

import com.edu.wing.freeBoard.domain.FreeBoardVo;

import java.util.List;
import java.util.Map;

public interface FreeBoardDao {

  int freeBoardSelectTotalCount(int noticeBoardNo, String freeBoardSearch);

  List<FreeBoardVo> freeBoardSelectList(Map<String, Object> map);

  FreeBoardVo freeBoardSelectOne(int freeBoardNo);

  int addFreeBoard(FreeBoardVo freeBoardVo);

  int updateFreeBoard(int freeBoardNo, String title, String content);
}

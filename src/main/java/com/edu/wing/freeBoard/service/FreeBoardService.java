package com.edu.wing.freeBoard.service;

import com.edu.wing.freeBoard.domain.FreeBoardVo;

import java.util.List;
import java.util.Map;

public interface FreeBoardService {

  int freeBoardSelectTotalCount(int noticeBoardNo, String freeBoardSearch);

  List<FreeBoardVo> freeBoardSelectList(int start, int end, String freeBoardSearch, int noticeBoardNo);

  FreeBoardVo freeBoardSelectOne(int freeBoardNo);
}

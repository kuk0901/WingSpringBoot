package com.edu.wing.freeBoardComment.service;

import com.edu.wing.freeBoardComment.dao.FreeBoardCommentDao;
import com.edu.wing.freeBoardComment.domain.FreeBoardCommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FreeBoardCommentServiceImpl implements FreeBoardCommentService{

  @Autowired
  private FreeBoardCommentDao freeBoardCommentDao;

  @Override
  public List<FreeBoardCommentVo> freeBoardCommentSelectList(int freeBoardNo) {
    return freeBoardCommentDao.freeBoardCommentSelectList(freeBoardNo);
  }


}

package com.edu.wing.freeBoardComment.dao;

import com.edu.wing.freeBoardComment.domain.FreeBoardCommentVo;

import java.util.List;

public interface FreeBoardCommentDao {

  List<FreeBoardCommentVo> freeBoardCommentSelectList(int freeBoardNo);

  int addComment(FreeBoardCommentVo freeBoardCommentVo);

  boolean deleteComment(int freeBoardCommentNo);
}

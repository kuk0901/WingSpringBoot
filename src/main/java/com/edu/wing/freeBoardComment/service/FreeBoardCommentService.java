package com.edu.wing.freeBoardComment.service;

import com.edu.wing.freeBoardComment.domain.FreeBoardCommentVo;

import java.util.List;

public interface FreeBoardCommentService {

  List<FreeBoardCommentVo> freeBoardCommentSelectList(int freeBoardNo);

  boolean addComment(int freeBoardNo, String content, int memberNo);

  boolean deleteComment(int freeBoardCommentNo);

  boolean updateComment(FreeBoardCommentVo freeBoardCommentVo);
}

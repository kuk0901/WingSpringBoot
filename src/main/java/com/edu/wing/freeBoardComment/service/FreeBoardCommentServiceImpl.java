package com.edu.wing.freeBoardComment.service;

import com.edu.wing.freeBoard.domain.FreeBoardVo;
import com.edu.wing.freeBoardComment.dao.FreeBoardCommentDao;
import com.edu.wing.freeBoardComment.domain.FreeBoardCommentVo;
import com.edu.wing.util.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.undo.CannotUndoException;
import java.util.List;

@Service
public class FreeBoardCommentServiceImpl implements FreeBoardCommentService{

  @Autowired
  private FreeBoardCommentDao freeBoardCommentDao;

  @Override
  public List<FreeBoardCommentVo> freeBoardCommentSelectList(int freeBoardNo) {
    return freeBoardCommentDao.freeBoardCommentSelectList(freeBoardNo);
  }

  @Override
  public boolean addComment(int freeBoardNo, String content, int memberNo) {
    FreeBoardCommentVo freeBoardCommentVo = new FreeBoardCommentVo();

    freeBoardCommentVo.setFreeBoardNo(freeBoardNo);
    freeBoardCommentVo.setContent(content);
    freeBoardCommentVo.setMemberNo(memberNo);

    return freeBoardCommentDao.addComment(freeBoardCommentVo) > 0;
  }

  @Override
  public boolean deleteComment(int freeBoardCommentNo) {
    return freeBoardCommentDao.deleteComment(freeBoardCommentNo);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public boolean updateComment(FreeBoardCommentVo freeBoardCommentVo) throws CustomException {

    // 1. 업데이트 시도!
    freeBoardCommentDao.updateComment(freeBoardCommentVo);

    // 2. 비교를 위한 셀렉트!
    FreeBoardCommentVo updateFreeBoardCommentVo = freeBoardCommentDao.compareFreeBoardComment(freeBoardCommentVo);

    // 3. 비교!
    return freeBoardCommentVo.equals(updateFreeBoardCommentVo);
    /*
    * 성공? true
    * 실패? false
    * */
  }

}

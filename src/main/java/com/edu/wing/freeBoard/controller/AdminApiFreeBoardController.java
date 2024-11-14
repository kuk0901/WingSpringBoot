package com.edu.wing.freeBoard.controller;

import com.edu.wing.freeBoard.domain.FreeBoardVo;
import com.edu.wing.freeBoard.service.FreeBoardService;
import com.edu.wing.freeBoardComment.domain.FreeBoardCommentVo;
import com.edu.wing.freeBoardComment.service.FreeBoardCommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/api/freeBoard")
public class AdminApiFreeBoardController {

  @Autowired
  private FreeBoardService freeBoardService;

  @Autowired
  private FreeBoardCommentService freeBoardCommentService;

  @DeleteMapping("/list/{freeBoardNo}/delete")
  public ResponseEntity<?> deleteFreeBoard(@PathVariable int freeBoardNo, @RequestParam String curPage, @RequestParam(defaultValue = "") String freeBoardSearch,
                                           @RequestParam(defaultValue = "3") int noticeBoardNo, HttpSession session){
    Map<String, Object> resultMap = new HashMap<>();

    resultMap.put("curPage", curPage);
    resultMap.put("freeBoardSearch", freeBoardSearch);
    resultMap.put("noticeBoardNo", noticeBoardNo);

    boolean deleteFreeBoard = freeBoardService.deleteFreeBoard(freeBoardNo);

    if(!deleteFreeBoard) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오률 인해 게시글을 삭제할 수 없습니다. 잠시 후 다시 시도해주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "게시글이 성공적으로 삭제되었습니다.");
    return ResponseEntity.ok().body(resultMap);
  }

  @DeleteMapping("list/{freeBoardCommentNo}/deleteComment")
  public ResponseEntity<?> deleteComment(@PathVariable int freeBoardCommentNo, @RequestParam(defaultValue = "1") String curPage
      , @RequestParam(defaultValue = "") String freeBoardSearch, @RequestParam int freeBoardNo){
    Map<String, Object> resultMap = new HashMap<>();

    resultMap.put("curPage", curPage);
    resultMap.put("freeBoardSearch", freeBoardSearch);
    resultMap.put("freeBoardNo", freeBoardNo);

    boolean deleteComment = freeBoardCommentService.deleteComment(freeBoardCommentNo);

    if(!deleteComment) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 삭제에 실패하였습니다. 잠시 후 다시 시도해주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    FreeBoardVo freeBoardVo = freeBoardService.freeBoardSelectOne(freeBoardNo);

    List<FreeBoardCommentVo> freeBoardCommentList = freeBoardCommentService.freeBoardCommentSelectList(freeBoardNo);

    resultMap.put("freeBoardVo", freeBoardVo);
    resultMap.put("freeBoardCommentList", freeBoardCommentList);
    resultMap.put("status", "success");
    resultMap.put("alertMsg", "댓글 삭제에 성공했습니다.");
    return ResponseEntity.ok().body(resultMap);
  }
}

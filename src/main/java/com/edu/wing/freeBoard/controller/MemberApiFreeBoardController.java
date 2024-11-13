package com.edu.wing.freeBoard.controller;

import com.edu.wing.freeBoard.domain.FreeBoardVo;
import com.edu.wing.freeBoard.service.FreeBoardService;
import com.edu.wing.freeBoardComment.service.FreeBoardCommentService;
import com.edu.wing.member.domain.MemberVo;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member/api/freeBoard")
public class MemberApiFreeBoardController {

  private static final Logger log = LoggerFactory.getLogger(MemberApiFreeBoardController.class);
  private static final String LOG_TITLE = "==MemberApiFreeBoardController==";

  @Autowired
  private FreeBoardService freeBoardService;

  @Autowired
  FreeBoardCommentService freeBoardCommentService;

  @PostMapping("/add")
  public ResponseEntity<?> addFreeBoard(@RequestBody FreeBoardVo freeBoardVo, HttpSession session) {
    log.info(LOG_TITLE);
    log.info("addFreeBoard freeBoardVo: {}", freeBoardVo);

    Map<String, Object> resultMap = new HashMap<>();

    MemberVo member = (MemberVo) session.getAttribute("member");

    String title = freeBoardVo.getTitle();
    String content = freeBoardVo.getContent();
    String email = freeBoardVo.getEmail();
    int noticeBoardNo = freeBoardVo.getNoticeBoardNo();

    boolean added = freeBoardService.addFreeBoard(title, content, email, noticeBoardNo, member.getMemberNo());

    if(!added) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 게시글을 추가할 수 없습니다. 잠시 후 다시 시도해주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "게시글 추가에 성공했습니다");
    return ResponseEntity.ok().body(resultMap);
  }

  @PatchMapping("/list/{freeBoardNo}/update")
  public ResponseEntity<?> updateFreeBoard(@PathVariable int freeBoardNo, @RequestBody Map<String, String> updateData
      , @RequestParam(defaultValue = "1") String curPage, @RequestParam(defaultValue = "") String freeBoardSearch
      , @RequestParam(defaultValue = "3") int noticeBoardNo) {
    log.info(LOG_TITLE);
    log.info("updateFreeBoard freeBoardNo: {}, updateData: {}, curPage: {}, freeBoardSearch: {}, noticeBoardNo: {}", freeBoardNo, updateData, curPage, freeBoardSearch, noticeBoardNo);

    Map<String, Object> resultMap = new HashMap<>();

    resultMap.put("curPage", curPage);
    resultMap.put("freeBoardSearch", freeBoardSearch);
    resultMap.put("noticeBoardNo", noticeBoardNo);

    boolean updateFreeBoard = freeBoardService.updateFreeBoard(freeBoardNo, updateData.get("title"), updateData.get("content"));

    if(!updateFreeBoard) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 게시글이 수정되지 않았습니다. 잠시 후 다시 시도해주세요");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "게시글이 성공적으로 수정되었습니다");
    return ResponseEntity.ok().body(resultMap);

  }

  @PostMapping("/list/{freeBoardNo}/addComment")
  public ResponseEntity<?> addComment(@PathVariable int freeBoardNo, @RequestBody Map<String, String> addData
      , @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "") String freeBoardSearch, @RequestParam(defaultValue = "3") int noticeBoardNo, HttpSession session){

    Map<String, Object> resultMap = new HashMap<>();

    resultMap.put("curPage", curPage);
    resultMap.put("freeBoardSearch", freeBoardSearch);
    resultMap.put("noticeBoardNo", noticeBoardNo);

    MemberVo member = (MemberVo) session.getAttribute("member");

    if (member == null) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "로그인이 필요한 서비스입니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    boolean added = freeBoardCommentService.addComment(freeBoardNo, addData.get("content"), member.getMemberNo());

    if (!added) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 답글을 추가할 수 없습니다. 잠시 후 다시 시도해주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "답글 추가에 성공했습니다");
    return ResponseEntity.ok().body(resultMap);
  }

}

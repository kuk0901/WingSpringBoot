package com.edu.wing.freeBoard.controller;

import com.edu.wing.freeBoard.domain.FreeBoardVo;
import com.edu.wing.freeBoard.service.FreeBoardService;
import com.edu.wing.freeBoardComment.domain.FreeBoardCommentVo;
import com.edu.wing.freeBoardComment.service.FreeBoardCommentService;
import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.util.CustomException;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member/api/freeBoard")
public class MemberApiFreeBoardController {

  @Autowired
  private FreeBoardService freeBoardService;

  @Autowired
  private FreeBoardCommentService freeBoardCommentService;

  @GetMapping("/list/{freeBoardNo}")
  public ResponseEntity<Map<String, Object>> freeBoardDetail(@PathVariable int freeBoardNo
      , @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "") String freeBoardSearch
      , @RequestParam(defaultValue = "3") int noticeBoardNo, HttpSession httpSession){

    Map<String, Object> resultMap = new HashMap<>();

    MemberVo member = (MemberVo) httpSession.getAttribute("member");

    FreeBoardVo freeBoardVo = freeBoardService.freeBoardSelectOne(freeBoardNo);

    List<FreeBoardCommentVo> freeBoardCommentList = freeBoardCommentService.freeBoardCommentSelectList(freeBoardNo);

    if(freeBoardVo == null){
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 정보를 불러올 수 없습니다. 잠시 후 다시 시도해 주세요.");

      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("curPage", curPage);
    resultMap.put("freeBoardSearch", freeBoardSearch);
    resultMap.put("noticeBoardNo", noticeBoardNo);
    resultMap.put("freeBoardVo", freeBoardVo);
    resultMap.put("freeBoardCommentList", freeBoardCommentList);
    resultMap.put("currentMemberNo", member.getMemberNo());

    return ResponseEntity.ok().body(resultMap);
  }

  @PostMapping("/add")
  public ResponseEntity<?> addFreeBoard(@RequestBody FreeBoardVo freeBoardVo, HttpSession session) {

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

  @GetMapping("/list/{freeBoardNo}/update")
  public ResponseEntity<?> updateFreeBoard(@PathVariable int freeBoardNo, @RequestParam String curPage, @RequestParam(defaultValue = "") String freeBoardSearch,
                                           @RequestParam(defaultValue = "3") int noticeBoardNo) {

    Map<String, Object> resultMap = new HashMap<>();

    resultMap.put("curPage", curPage);
    resultMap.put("freeBoardSearch", freeBoardSearch);
    resultMap.put("noticeBoardNo", noticeBoardNo);

    FreeBoardVo freeBoardVo = freeBoardService.freeBoardSelectOne(freeBoardNo);

    if(freeBoardVo == null){
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 정보를 불러올 수 없습니다. 잠시 후 다시 시도해주세요");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("freeBoardVo", freeBoardVo);

    return ResponseEntity.ok().body(resultMap);
  }

  @PatchMapping("/list/{freeBoardNo}/update")
  public ResponseEntity<?> updateFreeBoard(@PathVariable int freeBoardNo, @RequestBody Map<String, String> updateData
      , @RequestParam(defaultValue = "1") String curPage, @RequestParam(defaultValue = "") String freeBoardSearch
      , @RequestParam(defaultValue = "3") int noticeBoardNo, HttpSession session) {

    Map<String, Object> resultMap = new HashMap<>();

    MemberVo member = (MemberVo) session.getAttribute("member");

    resultMap.put("curPage", curPage);
    resultMap.put("freeBoardSearch", freeBoardSearch);
    resultMap.put("noticeBoardNo", noticeBoardNo);

    boolean updateFreeBoard = freeBoardService.updateFreeBoard(freeBoardNo, updateData.get("title"), updateData.get("content"));

    if(!updateFreeBoard) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 게시글이 수정되지 않았습니다. 잠시 후 다시 시도해주세요");
      return ResponseEntity.badRequest().body(resultMap);
    }

    FreeBoardVo freeBoardVo = freeBoardService.freeBoardSelectOne(freeBoardNo);

    List<FreeBoardCommentVo> freeBoardCommentList = freeBoardCommentService.freeBoardCommentSelectList(freeBoardNo);

    resultMap.put("freeBoardVo", freeBoardVo);
    resultMap.put("freeBoardCommentList", freeBoardCommentList);
    resultMap.put("currentMemberNo", member.getMemberNo());
    resultMap.put("status", "success");
    resultMap.put("alertMsg", "게시글이 성공적으로 수정되었습니다");

    return ResponseEntity.ok().body(resultMap);
  }

  @PostMapping("/list/{freeBoardNo}/addComment")
  public ResponseEntity<?> addComment(@PathVariable int freeBoardNo, @RequestBody Map<String, String> addData
      , @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "") String freeBoardSearch
      , @RequestParam(defaultValue = "3") int noticeBoardNo, HttpSession session){

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

    FreeBoardVo freeBoardVo = freeBoardService.freeBoardSelectOne(freeBoardNo);

    List<FreeBoardCommentVo> freeBoardCommentList = freeBoardCommentService.freeBoardCommentSelectList(freeBoardNo);

    resultMap.put("freeBoardCommentList", freeBoardCommentList);
    resultMap.put("freeBoardVo", freeBoardVo);
    resultMap.put("currentMemberNo", member.getMemberNo());
    resultMap.put("status", "success");
    resultMap.put("alertMsg", "답글 추가에 성공했습니다");
    return ResponseEntity.ok().body(resultMap);
  }

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
      , @RequestParam(defaultValue = "") String freeBoardSearch, @RequestParam int freeBoardNo, HttpSession session){
    Map<String, Object> resultMap = new HashMap<>();

    MemberVo member = (MemberVo) session.getAttribute("member");

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
    resultMap.put("currentMemberNo", member.getMemberNo());
    resultMap.put("status", "success");
    resultMap.put("alertMsg", "댓글 삭제에 성공했습니다.");
    return ResponseEntity.ok().body(resultMap);
  }

  @GetMapping("/list/{freeBoardCommentNo}/updateComment")
  public ResponseEntity<?> updateComment(@PathVariable int freeBoardCommentNo, @RequestParam String curPage, @RequestParam(defaultValue = "") String freeBoardSearch,
                                         @RequestParam(defaultValue = "3") int noticeBoardNo, @RequestParam int freeBoardNo, HttpSession httpSession) {
    Map<String, Object> resultMap = new HashMap<>();

    FreeBoardVo freeBoardVo = freeBoardService.freeBoardSelectOne(freeBoardNo);

    List<FreeBoardCommentVo> freeBoardCommentVo = freeBoardCommentService.freeBoardCommentSelectList(freeBoardNo);

    if (freeBoardVo == null){
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 정보를 불러올 수 없습니다. 잠시 후 다시 시도해주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    if (freeBoardCommentVo == null){
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 정보를 불러올 수 없습니다. 잠시 후 다시 시도해주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("curPage", curPage);
    resultMap.put("freeBoardCommentNo", freeBoardCommentNo);
    resultMap.put("noticeBoardNo", noticeBoardNo);
    resultMap.put("freeBoardNo", freeBoardNo);
    resultMap.put("freeBoardVo", freeBoardVo);
    resultMap.put("freeBoardCommentVo", freeBoardCommentVo);

    return ResponseEntity.ok().body(resultMap);


  }

  @PatchMapping("/list/{freeBoardCommentNo}/updateComment")
  public ResponseEntity<?> updateFreeBoardComment(@PathVariable int freeBoardCommentNo,  @RequestParam String freeBoardCommentContent, @RequestParam(defaultValue = "1") String curPage
      , @RequestParam(defaultValue = "") String freeBoardSearch, @RequestParam int freeBoardNo, HttpSession session ) {
    Map<String ,Object> resultMap = new HashMap<>();

    MemberVo member = (MemberVo) session.getAttribute("member");

    try {
      resultMap.put("curPage", curPage);
      resultMap.put("freeBoardSearch", freeBoardSearch);
      resultMap.put("freeBoardNo", freeBoardNo);

      FreeBoardCommentVo freeBoardCommentVo = new FreeBoardCommentVo();

      freeBoardCommentVo.setContent(freeBoardCommentContent);
      freeBoardCommentVo.setFreeBoardCommentNo(freeBoardCommentNo);

      if(!freeBoardCommentService.updateComment(freeBoardCommentVo)) {
        resultMap.put("status", "failed");
        resultMap.put("alertMsg", "서버 오류로 인해 댓글 수정이 불가능합니다. 잠시 후 다시 시도해주세요.");
        return ResponseEntity.badRequest().body(resultMap);
      }

      FreeBoardVo freeBoardVo = freeBoardService.freeBoardSelectOne(freeBoardNo);

      List<FreeBoardCommentVo> freeBoardCommentList = freeBoardCommentService.freeBoardCommentSelectList(freeBoardNo);

      resultMap.put("freeBoardVo", freeBoardVo);
      resultMap.put("freeBoardCommentList", freeBoardCommentList);
      resultMap.put("currentMemberNo", member.getMemberNo());
      resultMap.put("status", "success");
      resultMap.put("alertMsg", "댓글이 수정되었습니다.");
      return ResponseEntity.ok().body(resultMap);

    } catch (CustomException e) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "댓글 수정에 실패했습니다. 잠시 후 다시 시도해 주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    } catch (Exception e) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 댓글 수정에 실패했습니다. 관리자에게 문의해 주세요.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
  }
}

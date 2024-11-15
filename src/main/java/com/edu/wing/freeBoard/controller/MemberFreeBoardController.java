package com.edu.wing.freeBoard.controller;

import com.edu.wing.freeBoardComment.domain.FreeBoardCommentVo;
import com.edu.wing.freeBoardComment.service.FreeBoardCommentService;
import com.edu.wing.freeBoard.domain.FreeBoardVo;
import com.edu.wing.freeBoard.service.FreeBoardService;
import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.util.Paging;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member/freeBoard")
public class MemberFreeBoardController {

  private static final Logger log = LoggerFactory.getLogger(MemberFreeBoardController.class);
  private static final String LOG_TITLE = "==MemberFreeBoardController==";

  @Autowired
  private FreeBoardService freeBoardService;

  @Autowired
  private FreeBoardCommentService freeBoardCommentService;

  @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
  public ModelAndView freeBoardList(@RequestParam(defaultValue = "1") String curPage, @RequestParam(defaultValue = "") String freeBoardSearch,
                                    @RequestParam(defaultValue = "3") int noticeBoardNo) {

    int totalCount = freeBoardService.freeBoardSelectTotalCount(noticeBoardNo, freeBoardSearch);

    Paging pagingVo = new Paging(totalCount, Integer.parseInt(curPage));
    int start = pagingVo.getPageBegin();
    int end = pagingVo.getPageEnd();

    List<FreeBoardVo> freeBoardList = freeBoardService.freeBoardSelectList(start, end, freeBoardSearch, noticeBoardNo);

    Map<String, Object> pagingMap = new HashMap<>();
    pagingMap.put("totalCount", totalCount);
    pagingMap.put("pagingVo", pagingVo);
    pagingMap.put("curPage", curPage);

    ModelAndView mav = new ModelAndView("jsp/member/freeBoard/FreeBoardListView");

    mav.addObject("freeBoardList", freeBoardList);
    mav.addObject("pagingMap", pagingMap);
    mav.addObject("freeBoardSearch", freeBoardSearch);
    mav.addObject("noticeBoardNo", noticeBoardNo);

    return mav;

  }

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

  @GetMapping("/list/add")
  public ModelAndView freeBoardAdd(@RequestParam(defaultValue = "") String freeBoardSearch
          , @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "3") int noticeBoardNo, HttpSession httpSession) {

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = currentDate.format(formatter);

    MemberVo member = (MemberVo) httpSession.getAttribute("member");

    ModelAndView mav = new ModelAndView("jsp/member/freeBoard/FreeBoardAddView");

    mav.addObject("freeBoardSearch", freeBoardSearch);
    mav.addObject("curPage", curPage);
    mav.addObject("member", member);
    mav.addObject("currentDate", formattedDate);
    mav.addObject("noticeBoardNo", noticeBoardNo);

    return mav;

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

}

package com.edu.wing.freeBoard.controller;

import com.edu.wing.freeBoard.domain.FreeBoardVo;
import com.edu.wing.freeBoard.service.FreeBoardService;
import com.edu.wing.freeBoardComment.domain.FreeBoardCommentVo;
import com.edu.wing.freeBoardComment.service.FreeBoardCommentService;
import com.edu.wing.util.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/freeBoard")
public class AdminFreeBoardController {

  private static final Logger log = LoggerFactory.getLogger(AdminFreeBoardController.class);
  private static final String LOG_TITLE = "==AdminFreeBoardController==";

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

    ModelAndView mav = new ModelAndView("jsp/admin/freeBoard/FreeBoardListView");

    mav.addObject("freeBoardList", freeBoardList);
    mav.addObject("pagingMap", pagingMap);
    mav.addObject("freeBoardSearch", freeBoardSearch);
    mav.addObject("noticeBoardNo", noticeBoardNo);

    return mav;
  }

  @GetMapping("/list/{freeBoardNo}")
  public ResponseEntity<Map<String, Object>> freeBoardDetail(@PathVariable int freeBoardNo, @RequestParam(defaultValue = "1") int curPage
      , @RequestParam(defaultValue = "") String freeBoardSearch, @RequestParam(defaultValue = "2") int noticeBoardNo){

    Map<String, Object> resultMap = new HashMap<>();

    FreeBoardVo freeBoardVo = freeBoardService.freeBoardSelectOne(freeBoardNo);

    List<FreeBoardCommentVo> freeBoardCommentVoList = freeBoardCommentService.freeBoardCommentSelectList(freeBoardNo);

    if(freeBoardVo == null){
      resultMap.put("success", false);
      resultMap.put("alertMsg", "서버 오류로 인해 정보를 불러올 수 없습니다. 잠시 후 다시 시도해 주세요.");

      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("freeBoardVo", freeBoardVo);
    resultMap.put("freeBoardCommentVoList", freeBoardCommentVoList);
    resultMap.put("curPage", curPage);
    resultMap.put("freeBoardSearch", freeBoardSearch);
    resultMap.put("noticeBoardNo", noticeBoardNo);

    return ResponseEntity.ok().body(resultMap);
  }
}

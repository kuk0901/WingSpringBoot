package com.edu.wing.post.controller;

import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.post.domain.PostVo;
import com.edu.wing.post.service.PostService;
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
@RequestMapping("/admin/cs/post")
public class AdminPostController {

  private static final Logger log = LoggerFactory.getLogger(AdminPostController.class);
  private static final String LOG_TITLE = "==AdminPostController==";

  @Autowired
  private PostService postService;

  @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
  public ModelAndView postList(@RequestParam(defaultValue = "1") String curPage, @RequestParam(defaultValue = "") String postSearch,
                               @RequestParam(defaultValue = "2") int noticeBoardNo) {
    log.info(LOG_TITLE);
    log.info("@RequestMapping postList curPage: {}, noticeBoardNo: {}, postSearch: {}", curPage, noticeBoardNo, postSearch);

    int totalCount = postService.postSelectTotalCount(noticeBoardNo, postSearch);

    log.info("totalCount: {}", totalCount);

    Paging pagingVo = new Paging(totalCount, Integer.parseInt(curPage));
    int start = pagingVo.getPageBegin();
    int end = pagingVo.getPageEnd();

    List<PostVo> postList = postService.postSelectList(start, end, postSearch, noticeBoardNo);

    log.info("postList: {}", postList);

    Map<String, Object> pagingMap = new HashMap<>();
    pagingMap.put("totalCount", totalCount);
    pagingMap.put("pagingVo", pagingVo);
    pagingMap.put("curPage", curPage);

    ModelAndView mav = new ModelAndView("jsp/admin/post/PostListView");

    mav.addObject("postList", postList);
    mav.addObject("pagingMap", pagingMap);
    mav.addObject("postSearch", postSearch);
    mav.addObject("noticeBoardNo", noticeBoardNo);

    return mav;
  }

  @GetMapping("/list/add")
  public ModelAndView postAdd(@RequestParam(defaultValue = "2") int noticeBoardNo, @RequestParam String curPage, @RequestParam String postSearch, HttpSession httpSession) {
    log.info("{} - Retrieving @GetMapping postAdd", LOG_TITLE);
    log.info("@RequestMapping postAdd curPage: {}, postSearch: {}", curPage, postSearch);

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = currentDate.format(formatter);

    MemberVo member = (MemberVo) httpSession.getAttribute("member");

    if (member == null) {
      log.warn("Member is not found in session.");
    } else {
      log.info("Member found: {}", member);
    }

    ModelAndView mav = new ModelAndView("jsp/admin/post/PostAddView");
    mav.addObject("currentDate", formattedDate);
    mav.addObject("member", member);
    mav.addObject("noticeBoardNo", noticeBoardNo);
    mav.addObject("curPage", curPage);
    mav.addObject("postSearch", postSearch);

    return mav;
  }

  @GetMapping("/{postNo}")
  public ResponseEntity<Map<String, Object>> postDetail(@PathVariable int postNo, @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "") String postSearch,
                                                        @RequestParam(defaultValue = "2") int noticeBoardNo) {
    log.info(LOG_TITLE);
    log.info("@RequestMapping postDetail postNo: {}, curPage: {}, postSearch: {}, noticeBoardNo: {}", postNo, curPage, postSearch, noticeBoardNo);

    Map<String, Object> resultMap = new HashMap<>();

    Map<String, Object> postVo = postService.postSelectOne(postNo);

    if (postVo == null) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 정보를 불러올 수 없습니다. 잠시 후 다시 시도해 주세요.");

      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("curPage", curPage);
    resultMap.put("postSearch", postSearch);
    resultMap.put("noticeBoardNo", noticeBoardNo);
    resultMap.put("postVo", postVo);

    return ResponseEntity.ok().body(resultMap);
  }

}

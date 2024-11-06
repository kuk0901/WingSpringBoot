package com.edu.wing.post.controller;

import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.post.domain.PostVo;
import com.edu.wing.post.service.PostService;
import com.edu.wing.util.Paging;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    log.info("@RequestMapping postList curPage: {}, noticeBoardNo: {}", curPage, noticeBoardNo);

    int totalCount = postService.postSelectTotalCount(noticeBoardNo);

    log.info("totalCount: {}", totalCount);

    Paging pagingVo = new Paging(totalCount, Integer.parseInt(curPage));
    int start = pagingVo.getPageBegin();
    int end = pagingVo.getPageEnd();

    List<PostVo> postList = postService.postSelectList(start, end, postSearch, noticeBoardNo);

    log.info("postList: {}", postList);

    Map<String, Object> pagingMap = new HashMap<>();
    pagingMap.put("totalCount", totalCount);
    pagingMap.put("pagingVo", pagingVo);

    ModelAndView mav = new ModelAndView("jsp/admin/post/PostListView");

    mav.addObject("postList", postList);
    mav.addObject("pagingMap", pagingMap);
    mav.addObject("postSearch", postSearch);

    return mav;
  }

  @GetMapping("/list/add")
  public ModelAndView postAdd(HttpSession httpSession) {
    log.info("{} - Retrieving @GetMapping postAdd", LOG_TITLE);

    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = currentDate.format(formatter);

    MemberVo member = (MemberVo) httpSession.getAttribute("member");

    ModelAndView mav = new ModelAndView("jsp/admin/post/PostAddView");
    mav.addObject("currentDate", formattedDate);
    mav.addObject("member", member);

    return mav;
  }

}

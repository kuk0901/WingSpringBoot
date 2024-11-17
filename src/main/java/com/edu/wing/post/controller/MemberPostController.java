package com.edu.wing.post.controller;

import com.edu.wing.post.domain.PostVo;
import com.edu.wing.post.service.PostService;
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
@RequestMapping("/member/cs/post")
public class MemberPostController {

  @Autowired
  private PostService postService;

  @RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
  public ModelAndView postList(@RequestParam(defaultValue = "1") String curPage, @RequestParam(defaultValue = "") String postSearch,
                               @RequestParam(defaultValue = "2") int noticeBoardNo) {

    int totalCount = postService.postSelectTotalCount(noticeBoardNo, postSearch);

    Paging pagingVo = new Paging(totalCount, Integer.parseInt(curPage));
    int start = pagingVo.getPageBegin();
    int end = pagingVo.getPageEnd();

    List<PostVo> postList = postService.postSelectList(start, end, postSearch, noticeBoardNo);

    Map<String, Object> pagingMap = new HashMap<>();
    pagingMap.put("totalCount", totalCount);
    pagingMap.put("pagingVo", pagingVo);
    pagingMap.put("curPage", curPage);

    ModelAndView mav = new ModelAndView("jsp/member/post/PostListView");

    mav.addObject("postList", postList);
    mav.addObject("pagingMap", pagingMap);
    mav.addObject("postSearch", postSearch);
    mav.addObject("noticeBoardNo", noticeBoardNo);

    return mav;
  }

}

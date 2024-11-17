package com.edu.wing.post.controller;

import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.post.domain.PostVo;
import com.edu.wing.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("member/api/cs/post")
public class MemberApiPostController {

  @Autowired
  private PostService postService;

  @GetMapping("/list/{postNo}")
  public ResponseEntity<Map<String, Object>> postDetail(@PathVariable int postNo, @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "") String postSearch,
                                                        @RequestParam(defaultValue = "2") int noticeBoardNo) {

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

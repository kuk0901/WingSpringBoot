package com.edu.wing.post.controller;

import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.post.domain.PostVo;
import com.edu.wing.post.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("admin/api/cs/post")
public class AdminApiPostController {

  private static final Logger log = LoggerFactory.getLogger(AdminApiPostController.class);
  private static final String LOG_TITLE = "==AdminApiPostController==";

  @Autowired
  private PostService postService;

  @PostMapping("/list/add")
  public ResponseEntity<?> addPost(@RequestBody PostVo postVo, HttpSession httpSession) {
    log.info(LOG_TITLE);
    log.info("addPost postVo: {}", postVo);

    Map<String, Object> resultMap = new HashMap<>();

    MemberVo member = (MemberVo) httpSession.getAttribute("member");

    if (member == null) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "로그인 정보가 없습니다.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    postVo.setMemberNo(member.getMemberNo());

    boolean addPost = postService.addPost(postVo);

    if(!addPost) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 공지사항이 등록되지 않았습니다. 잠시 후 다시 시도해주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "공지사항이 성공적으로 등록되었습니다.");
    return ResponseEntity.ok().body(resultMap);

  }

  @PatchMapping("/list/{postNo}/update")
  public ResponseEntity<?> postUpdate(@PathVariable int postNo,  @RequestBody Map<String, String> updateData, @RequestParam(defaultValue = "1") String curPage, @RequestParam(defaultValue = "") String postSearch,
                                      @RequestParam(defaultValue = "2") int noticeBoardNo, HttpSession httpSession) {
    log.info(LOG_TITLE);
    log.info("updatePost postNo: {}, updateData: {}, curPage: {}, postSearch: {}, noticeBoardNo: {}", postNo, updateData, curPage, postSearch, noticeBoardNo);

    Map<String, Object> resultMap = new HashMap<>();

    MemberVo member = (MemberVo) httpSession.getAttribute("member");

    boolean updatePost = postService.updatePost(postNo, updateData.get("title"), updateData.get("content"), member.getMemberNo());

    if (!updatePost) {
      resultMap.put("status", "failed");
      resultMap.put("alertMsg", "서버 오류로 인해 공지사항이 수정되지 않았습니다. 잠시 후 다시 시도해 주세요.");
      return ResponseEntity.internalServerError().body(resultMap);
    }

    resultMap.put("status", "success");
    resultMap.put("alertMsg", "공지사항이 수정되었습니다.");
    return ResponseEntity.ok().body(resultMap);
  }

  @DeleteMapping("/list/{postNo}/delete")
  public ResponseEntity<?> deletePost(@PathVariable int postNo, @RequestParam String curPage, @RequestParam(defaultValue = "") String postSearch,
                                      @RequestParam(defaultValue = "2") int noticeBoardNo) {
    log.info(LOG_TITLE);
    log.info("deletePost postNo: {}, curPage: {}, postSearch: {}", postNo, curPage, noticeBoardNo);

    Map<String, Object> resultMap = new HashMap<>();

    try {
      // 실제 삭제 작업 수행
      boolean deletePost = postService.deletePost(postNo);

      if (!deletePost) {
        resultMap.put("status", "error");
        resultMap.put("alertMsg", "공지사항 삭제에 실패했습니다.");
        return ResponseEntity.badRequest().body(resultMap);
      }

      resultMap.put("status", "success");
      resultMap.put("alertMsg", "공지사항이 성공적으로 삭제되었습니다.");

    } catch (Exception e) {
      resultMap.put("status", "error");
      resultMap.put("alertMsg", "서버 오류로 인해 삭제에 실패했습니다. 잠시 후 다시 시도해 주세요.");
      return ResponseEntity.badRequest().body(resultMap);
    }

    // 페이지 정보 추가
    resultMap.put("curPage", curPage);
    resultMap.put("postSearch", postSearch);
    resultMap.put("noticeBoardNo", noticeBoardNo);

    return ResponseEntity.ok(resultMap);
  }

}

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

        MemberVo member = (MemberVo) httpSession.getAttribute("member");

        if (member != null) {
            postVo.setMemberNo(member.getMemberNo()); // memberNo를 InquiryVo에 설정
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다.");
        }

        try {
            postService.addPost(postVo);
            return ResponseEntity.ok().body("문의가 성공적으로 등록되었습니다");
        }catch (Exception e) {
            log.error("Error occurred while adding post: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("문의 등록 중 오류가 발생했습니다");
        }
    }
}

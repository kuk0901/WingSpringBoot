package com.edu.wing.member.controller;

import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.member.service.MemberService;
import com.edu.wing.util.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/member")
public class AdminMemberApiController {
    private Logger log = LoggerFactory.getLogger(AdminMemberApiController.class);
    private final String logTitleMsg = "==AdminMemberApiController==";

    @Autowired
    private MemberService memberService;

    //초기화면
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST}) // 관리자용 회원 목록 페이지
    public ModelAndView getAllMembersForAdmin(@RequestParam(defaultValue = "1") int curPage) {
        log.info(logTitleMsg);
        log.info("@RequestMapping getAllMembersForAdmin curPage: {}", curPage);
        int totalCount = memberService.selectTotalMembersCount();
        Paging pagingVo = new Paging(totalCount, curPage);
        int start = pagingVo.getPageBegin();
        int end = pagingVo.getPageEnd();
        List<MemberVo> memberList = memberService.selectAllMembersForAdmin(start,end);

        Map<String, Object> pagingMap = new HashMap<>();
        pagingMap.put("totalCount", totalCount);
        pagingMap.put("pagingVo", pagingVo);

        ModelAndView mav = new ModelAndView("jsp/admin/member/adminMember");
        mav.addObject("memberList", memberList);
        mav.addObject("pagingMap", pagingMap);

        return mav;
    }
    @GetMapping("/{memberNo}")
    public ResponseEntity<Map<String, Object>> selectMemberDetailForAdmin(@PathVariable int memberNo, @RequestParam int curPage) {
        log.info(logTitleMsg);
        log.info("@GetMapping memberDetail memberNo: {}, curPage: {}", memberNo, curPage);

        // 회원 상세 정보를 가져오는 서비스 메서드 호출
        Map<String, Object> resultMap = memberService.selectMemberDetailForAdmin(memberNo);

        // 현재 페이지 정보 추가
        resultMap.put("curPage", curPage);

        return ResponseEntity.ok(resultMap);
    }
   
}

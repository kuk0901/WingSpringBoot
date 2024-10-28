package com.edu.wing.member.controller;

import com.edu.wing.accountbook.service.AccountBookService;
import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.member.service.MemberService;
import com.edu.wing.util.Paging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/admin/member")
@Controller
public class AdminMemberController {


    private Logger log = LoggerFactory.getLogger(AdminMemberApiController.class);
    private final String logTitleMsg = "==AdminMemberApiController==";

    @Autowired
    private MemberService memberService;
    @Autowired
    private AccountBookService accountBookService;
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
}

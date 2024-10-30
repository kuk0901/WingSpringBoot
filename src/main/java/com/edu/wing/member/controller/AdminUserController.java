package com.edu.wing.member.controller;

import com.edu.wing.accountbook.service.AccountBookService;
import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/admin/user")
@Controller
public class AdminUserController {

    private Logger log = LoggerFactory.getLogger(AdminUserController.class);
    private final String logTitleMsg = "==AdminUserController==";

    @Autowired
    private MemberService memberService;
    @Autowired
    private AccountBookService accountBookService;

    // 관리자 마이페이지 GET 요청 처리
    @GetMapping("/myPage/{memberNo}")
    public ModelAndView getAdminMypage(@PathVariable("memberNo") int memberNo) {
        MemberVo memberInfo = memberService.getAdminMypageInfo(memberNo);

        ModelAndView mav = new ModelAndView("jsp/admin/user/AdminMyPageView"); // JSP 파일 경로
        mav.addObject("memberInfo", memberInfo);
        return mav;
    }

}

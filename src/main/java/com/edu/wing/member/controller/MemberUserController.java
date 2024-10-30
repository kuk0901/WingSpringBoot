package com.edu.wing.member.controller;

import com.edu.wing.member.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/member/user")
@Controller
public class MemberUserController {
    private Logger log = LoggerFactory.getLogger(MemberUserController.class);
    private final String logTitleMsg = "==MemberUserController==";

    @Autowired
    private MemberService memberService;

    // 마이페이지 화면 이동
    @RequestMapping("/myPage")
    public String myPage() {
        log.info(logTitleMsg + " 마이페이지로 이동");
        return "jsp/member/user/UserMyPageView";
    }
}

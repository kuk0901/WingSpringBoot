package com.edu.wing.accountbook.controller;

import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.accountbook.service.AccountBookService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/member/accountBook")
@Controller
public class MemberAccountBookController {
    private Logger log = LoggerFactory.getLogger(MemberAccountBookController.class);
    private final String logTitleMsg = "==MemberAccountBookController==";

    @Autowired
    private AccountBookService accountBookService;

    //로그인후->초기화면이동용
    @GetMapping("/list")
    public String getAccountBookPage() {
        return "jsp/member/accountBook/accountBook"; // JSP 파일 경로
    }
}


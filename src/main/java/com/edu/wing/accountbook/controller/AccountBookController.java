package com.edu.wing.accountbook.controller;

import org.springframework.ui.Model;
import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.accountbook.service.AccountBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/accountBook")
@Controller
public class AccountBookController {

    @Autowired
    private AccountBookService accountBookService;

    // admin 계좌부 리스트
    @GetMapping("/admin/list")
    public String listAccountBooks(Model model) {
        List<AccountBookVo> accountBookList = accountBookService.selectAccountBook();
        model.addAttribute("accountBookList", accountBookList);
        return "jsp/admin/accountBook/accountBook"; // JSP 페이지 경로
    }
}

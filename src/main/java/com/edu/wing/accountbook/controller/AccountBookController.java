package com.edu.wing.accountbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.accountbook.service.AccountBookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/admin/accountBook")
@Controller
public class AccountBookController {
  private Logger log = LoggerFactory.getLogger(AccountBookController.class);
  private final String logTitleMsg = "==AccountBookController==";

  @Autowired
  private AccountBookServiceImpl accountBookService;

  //초기화면용
  @GetMapping("/list")
  public String getAccountBookList(Model model) {
    log.info(logTitleMsg);
    // 초기 화면 데이터 조회
    List<AccountBookVo> accountBookList = accountBookService.selectAccountBook();

    // 카테고리와 결제 방법 리스트 가져오기
    List<String> categoryList = accountBookService.getMinusCategoryList();
    List<String> paymentMethodList = accountBookService.getPaymentMethodList();

    // 모델에 데이터를 담아 JSP로 전달
    model.addAttribute("accountBookList", accountBookList);
    model.addAttribute("categoryList", categoryList);
    model.addAttribute("paymentMethodList", paymentMethodList);

    return "jsp/admin/accountBook/accountBook";  // JSP 파일 경로
  }
}
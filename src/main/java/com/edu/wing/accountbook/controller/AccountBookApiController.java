package com.edu.wing.accountbook.controller;

import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.accountbook.service.AccountBookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accountBook")
public class AccountBookApiController {

    private Logger log = LoggerFactory.getLogger(AccountBookApiController.class);
    private final String logTitleMsg = "==AccountBookApiController==";

    @Autowired
    private AccountBookService accountBookService;

    // 카테고리 목록 조회
    @GetMapping("/admin/categories")
    public List<String> getCategories() {
        return accountBookService.getCategoryList();
    }

    // 결제 방법 목록 조회
    @GetMapping("/admin/paymentMethods")
    public List<String> getPaymentMethods() {
        return accountBookService.getPaymentMethodList();
    }

    // 특정 조건으로 가계부 목록 조회
    @GetMapping("/admin/list")
    public List<AccountBookVo> getAccountBooks(@RequestParam(required = false) String category,
                                               @RequestParam(required = false) String paymentMethod) {
        Map<String, Object> params = new HashMap<>();
       /* params.put("category", category);
        params.put("paymentMethod", paymentMethod);*/
        // 카테고리가 "all"인 경우 null로 설정-쿼리에서 null처리
        // 'all'이면 모든 항목을 검색하도록 설정
        params.put("category", category != null && category.equals("all") ? "all" : category);
        params.put("paymentMethod", paymentMethod != null && paymentMethod.equals("all") ? "all" : paymentMethod);
        return accountBookService.getAccountBooks(params);
    }

}

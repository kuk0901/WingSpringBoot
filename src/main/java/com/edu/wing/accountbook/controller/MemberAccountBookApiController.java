package com.edu.wing.accountbook.controller;

import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.accountbook.service.AccountBookService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate; // LocalDate 클래스 import
import org.springframework.format.annotation.DateTimeFormat; // DateTimeFormat 애너테이션 import

import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@RequestMapping("/member/api/accountBook")
public class MemberAccountBookApiController {

    private Logger log = LoggerFactory.getLogger(MemberAccountBookApiController.class);
    private final String logTitleMsg = "==AccountBookApiController==";

    @Autowired
    private AccountBookService accountBookService;

    // @RequestParam: HTTP 요청에서 전달된 파라미터를 메서드의 인자로 받을 수 있도록 함
    // @DateTimeFormat(iso = DateTimeFormat.ISO.DATE):
    //  - 이 애너테이션은 문자열 형식의 날짜를 LocalDate 객체로 변환하는 데 사용됩니다.
    //  - 'iso = DateTimeFormat.ISO.DATE'는 날짜 형식이 ISO 8601 형식(예: YYYY-MM-DD)임을 나타냅니다.
    //  - 이를 통해 클라이언트가 보내는 날짜 형식이 맞는지 자동으로 검사하고 변환합니다.
    // LocalDate date: 변환된 날짜를 저장하는 LocalDate 타입의 변수
    @GetMapping("/list/date")
    public ResponseEntity<List<AccountBookVo>> getAccountBooksByMonth(
            @RequestParam int memberNo,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "5") int limit) { // 기본값으로 10개 설정

        List<AccountBookVo> accountBooks = accountBookService.getAccountBooksByMonth(memberNo, startDate, endDate, limit);
        return ResponseEntity.ok(accountBooks);
    }
    @GetMapping("/list")
    public ResponseEntity<List<AccountBookVo>> getRecentAccountBooks(
            @RequestParam int memberNo,
            @RequestParam(defaultValue = "5") int limit) { // 기본값으로 10개 설정
        List<AccountBookVo> accountBooks = accountBookService.getAccountBooksByRecentDate(memberNo, limit);
        return ResponseEntity.ok(accountBooks);
    }
    // 카테고리 지출목록 조회,수입
    @GetMapping("/minusCategories")
    public List<String> getMinusCategories() {
        return accountBookService.getMinusCategoryList();
    }@GetMapping("/plusCategories")
    public List<String> getPlusCategories() {
        return accountBookService.getPlusCategoryList();
    }

    // 결제 방법 목록 조회
    @GetMapping("/paymentMethods")
    public List<String> getPaymentMethods() {
        return accountBookService.getPaymentMethodList();
    }

    @GetMapping("/monthlyCount")
    public int getMonthlyEntryCount(
            @RequestParam int memberNo,
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return accountBookService.getMonthlyEntryCount(memberNo, startDate, endDate);
    }

    @GetMapping("/monthlyEntries")
    public List<AccountBookVo> getMonthlyEntries(
            @RequestParam int memberNo,
            @RequestParam String startDate,
            @RequestParam String endDate) {

        return accountBookService.getMonthlyEntries(memberNo, startDate, endDate);
    }

    // 가계부 추가 API
    @PostMapping("/add")
    public ResponseEntity<String> addAccountBook(@RequestBody Map<String, Object> params) {
        try {

            int result = accountBookService.addAccountBook(params);
            if (result > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).body("가계부가 성공적으로 추가되었습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("가계부 추가에 실패했습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }


    @GetMapping("/detail")
    public ResponseEntity<?> getAccountBookDetail(
            @RequestParam("accountBookNo") int accountBookNo,
            @RequestParam("memberNo") int memberNo) {

        try {
            AccountBookVo accountBook = accountBookService.getAccountBookDetail(accountBookNo, memberNo);

            if (accountBook != null) {
                return ResponseEntity.ok(accountBook);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account book entry not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();  // 서버 로그에 예외 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching account book details.");
        }




    }
    // 가계부 삭제 API
    @DeleteMapping("/{accountBookNo}")
    public ResponseEntity<Void> deleteAccountBook(@PathVariable int accountBookNo) {
        accountBookService.deleteAccountBook(accountBookNo);
        return ResponseEntity.noContent().build(); // 204 No Content 반환
    }
    @PutMapping("/update")
    public ResponseEntity<String> updateAccountBook(@RequestBody Map<String, Object> params) {


        int updatedRows = accountBookService.updateAccountBook(params);

        if (updatedRows > 0) {
            return ResponseEntity.ok("가계부가 성공적으로 업데이트되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 실패");
        }
    }



}//종료


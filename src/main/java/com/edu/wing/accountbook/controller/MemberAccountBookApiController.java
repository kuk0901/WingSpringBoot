package com.edu.wing.accountbook.controller;

import com.edu.wing.accountbook.domain.AccountBookVo;
import com.edu.wing.accountbook.service.AccountBookService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate; // LocalDate 클래스 import
import org.springframework.format.annotation.DateTimeFormat; // DateTimeFormat 애너테이션 import

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
            @RequestParam(defaultValue = "5") int limit) {

        List<AccountBookVo> accountBooks = accountBookService.getAccountBooksByMonth(memberNo, startDate, endDate, limit);
        return ResponseEntity.ok(accountBooks);
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccountBookVo>> getRecentAccountBooks(
            @RequestParam int memberNo,
            @RequestParam(defaultValue = "1") int limit) { // 기본값으로 10개 설정
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
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return accountBookService.getMonthlyEntryCount(memberNo, startDate, endDate);
    }

    @GetMapping("/monthlyEntries")
    public List<AccountBookVo> getMonthlyEntries(
            @RequestParam int memberNo,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        return accountBookService.getMonthlyEntries(memberNo, startDate, endDate);
    }
    
    //가계부 추가
    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> addAccountBook(@RequestBody Map<String, Object> params) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            int result = accountBookService.addAccountBook(params);
            if (result > 0) {
                // 성공 시 상태와 메시지를 resultMap에 담아서 반환
                resultMap.put("status", "success");
                resultMap.put("alertMsg", "가계부가 추가되었습니다");
                return ResponseEntity.status(HttpStatus.CREATED).body(resultMap);
            } else {
                // 실패 시 상태와 메시지를 resultMap에 담아서 반환
                resultMap.put("status", "failure");
                resultMap.put("alertMsg", "가계부 추가에 실패했습니다.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 상태와 메시지를 resultMap에 담아서 반환
            resultMap.put("status", "error");
            resultMap.put("alertMsg", "서버 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
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
    @PatchMapping("/{accountBookNo}")
    public ResponseEntity<Map<String, String>> softDeleteAccountBook(@PathVariable int accountBookNo) {
        Map<String, String> resultMap = new HashMap<>();

        try {
            accountBookService.softDeleteAccountBook(accountBookNo);

            // 성공적인 삭제 후 상태와 메시지를 resultMap에 담기
            resultMap.put("status", "success");
            resultMap.put("alertMsg", "가계부가 성공적으로 삭제되었습니다.");

            return ResponseEntity.status(HttpStatus.OK).body(resultMap); // 200 OK 상태 코드와 resultMap 반환
        } catch (Exception e) {
            e.printStackTrace();

            // 예외 발생 시 상태와 메시지를 resultMap에 담기
            resultMap.put("status", "error");
            resultMap.put("alertMsg", "가계부 삭제에 실패했습니다.");

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap); // 500 상태 코드 반환
        }
    }
    @PutMapping("/update")
    public ResponseEntity<Map<String, String>> updateAccountBook(@RequestBody Map<String, Object> params) {
        Map<String, String> resultMap = new HashMap<>();

        try {
            int updatedRows = accountBookService.updateAccountBook(params);

            if (updatedRows > 0) {
                // 업데이트 성공 시 상태와 메시지를 resultMap에 담기
                resultMap.put("status", "success");
                resultMap.put("alertMsg", "가계부가 성공적으로 업데이트되었습니다.");
                return ResponseEntity.status(HttpStatus.OK).body(resultMap); // 200 OK 상태 코드와 resultMap 반환
            } else {
                // 업데이트 실패 시 상태와 메시지를 resultMap에 담기
                resultMap.put("status", "failure");
                resultMap.put("alertMsg", "업데이트 실패");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap); // 500 상태 코드 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 상태와 메시지를 resultMap에 담기
            resultMap.put("status", "error");
            resultMap.put("alertMsg", "서버 오류가 발생했습니다.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap); // 500 상태 코드 반환
        }
    }
    // 월별 지출 조회 API
    @GetMapping("/list/monthlyExpense")
    public ResponseEntity<List<AccountBookVo>> getMonthlyExpenseBook(
            @RequestParam int memberNo,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<AccountBookVo> accountBookVo = accountBookService.selectMonthlyExpenseBook(memberNo, startDate, endDate);
        return ResponseEntity.ok(accountBookVo);
    }
    // 월별 지출 조회 API
    @GetMapping("/list/monthlyIncome")
    public ResponseEntity<List<AccountBookVo>> getMonthlyIncomeBook(
            @RequestParam int memberNo,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {

        List<AccountBookVo> accountBookVo = accountBookService.selectMonthlyIncomeBook(memberNo, startDate, endDate);
        return ResponseEntity.ok(accountBookVo);
    }
    @GetMapping("/list/monthlyPayback")
    public ResponseEntity<List<AccountBookVo>> getMonthlyPayback(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam int memberNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("memberNo", memberNo);

        List<AccountBookVo> paybackAmounts = accountBookService.getMonthlyPayback(params);
        return ResponseEntity.ok(paybackAmounts);
    }

    @GetMapping("/myPage/detail")
    public ResponseEntity<List<AccountBookVo>> getCardDetail(
            @RequestParam int memberNo,
            @RequestParam(required = false) Integer categoryNo,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate
    ){
        // 날짜 및 카테고리 조건을 고려해 데이터 조회
        List<AccountBookVo> cardDetails = accountBookService.getCardDetailForMypage(memberNo, categoryNo, startDate);

        return ResponseEntity.ok(cardDetails);
    }

}//종료


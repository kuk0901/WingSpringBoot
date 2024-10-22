package com.edu.wing.paymentMethod.controller;

import com.edu.wing.category.controller.CategoryController;
import com.edu.wing.category.domain.CategoryVo;
import com.edu.wing.member.domain.MemberVo;
import com.edu.wing.paymentMethod.domain.PaymentMethodVo;
import com.edu.wing.paymentMethod.service.PaymentMethodService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/api/paymentMethod")
public class PaymentMethodController {
  private static final Logger log = LoggerFactory.getLogger(CategoryController.class);
  private static final String LOG_TITLE = "==PaymentMethodController==";

  @Autowired
  private PaymentMethodService paymentMethodService;

  @RequestMapping(value = "/list")
  public ModelAndView paymentMethodSelectList() {
    log.info("{} - Retrieving paymentMethod list", LOG_TITLE);

    List<PaymentMethodVo> paymentMethodVoList = paymentMethodService.paymentMethodSelectList();

    ModelAndView mav = new ModelAndView("jsp/admin/paymentMethod/PaymentMethodListView");

    mav.addObject("paymentMethodVoList", paymentMethodVoList);

    return mav;
  }

  @GetMapping("/add")
  public ModelAndView paymentMethodAdd() {
    log.info("{} - Retrieving @GetMapping paymentMethod add", LOG_TITLE);

    return new ModelAndView("jsp/admin/paymentMethod/PaymentMethodAddView");
  }

//  @PostMapping("/add")
//  public ResponseEntity<?> paymentMethodAdd(PaymentMethodVo paymentMethodVo) {
//    log.info("{} - Retrieving @PostMapping paymentMethod add", LOG_TITLE);
//
//    // 결데수단 추가와 관련된 코드 작성
//    // 0. 중복검사 select문으로
//    // 1. insert문 -> void 반환
//    // 2. select문으로 데이터가 삽입됐는지 조회 -> where절을 통해서 Vo의 값을 전달, 결데수단 명
//    // -> payment_method_no를 반환, 이게 있으면 trye 없으면 false (서비스에서) -> 서비스는 boolean을 반환, dao는 int를 반화
//    // 3. controller에서 조건문 사용 if로 boolean 값을 확인해서 통과되는 부분(블럭)은 ok로 반환
//    // -> resultMap.put("status", "success")
//    // 4. 조건문 불통과면 resultMap.put("status", "failed") -> Res~~~
//
//    // .body(resultMap);
//    return ResponseEntity.ok().body("123");
//  }

//  @PostMapping("/add")
//  public ResponseEntity<Map<String, String>> addPaymentMethod(@RequestBody PaymentMethodVo paymentMethodVo) {
//    Map<String, String> resultMap = new HashMap<>();
//
//    boolean isAdded = paymentMethodService.addPaymentMethod(paymentMethodVo);
//
//    if (isAdded) {
//      resultMap.put("status", "success");
//      return ResponseEntity.ok(resultMap);
//    } else {
//      resultMap.put("status", "failed");
//      return ResponseEntity.badRequest().body(resultMap);
//    }
//  }

  @PostMapping("/add")
  public ResponseEntity<?> addPaymentMethod(@RequestParam String paymentMethodName) {
    log.info(LOG_TITLE);
    log.info("paymentMethodAdd paymentMethodName: {}", paymentMethodName);

    Map<String, String> resultMap = new HashMap<>();

    try {
      // 결제 방법 이름이 이미 존재하는지 확인
      if (paymentMethodService.paymentMethodExist(paymentMethodName)) {
        resultMap.put("status", "fail");
        resultMap.put("msg", "이미 존재하는 결제 수단 이름입니다.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resultMap);
      }

      // 새로운 결제 방법 추가
      boolean isAdded = paymentMethodService.addPaymentMethod(paymentMethodName);
      if (isAdded) {
        resultMap.put("status", "success");
        resultMap.put("msg", "결제 수단이 성공적으로 추가되었습니다.");
        return ResponseEntity.ok().body(resultMap);
      }

      resultMap.put("status", "fail");
      resultMap.put("msg", "결제 수단 추가에 실패했습니다.");
      return ResponseEntity.internalServerError().body(resultMap);

    } catch (Exception e) {
      log.error("결제 수단 추가 중 오류 발생", e);
      resultMap.put("status", "error");
      resultMap.put("msg", "서버 오류가 발생했습니다.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
  }

  @GetMapping("/delete/{paymentMethodNo}")
  public ResponseEntity<?> getPaymentMethodTotalCount(@PathVariable int paymentMethodNo) {
    log.info(LOG_TITLE);
    log.info("paymentMethodTotalCount paymentMethodNo: {}", paymentMethodNo);

    Map<String, Object> resultMap = new HashMap<>();

    int count = paymentMethodService.pmTotalCount(paymentMethodNo);

    if (count > 0) {
      resultMap.put("status", "success");
      resultMap.put("totalCount", count);
      resultMap.put("msg", "해당 결제수단으로 작성된 가계부의 게시글이 " + count + "개입니다. 해당 결제수단을 삭제할 수 없습니다.");

      return ResponseEntity.ok().body(resultMap);
    }

    return ResponseEntity.ok().body(resultMap);
  }

//  SELECT COUNT(ACCOUNT_BOOK_NO)
//  FROM ACCOUNT_BOOK AB
//  WHERE AB.PAYMENT_METHOD_NO = 1;
//
//-- 1번 조회
//-- 반환되는게 총 개수
//-- 총 개수가 0이어야 지울 수 있다
//-- 조건문을 통해 0보다 크면 바로 return (메시지 같이) => 총 개수가 해당 결제수단으로 작성된 가계부의 게시글이 totalCount라서 안 됨
//-- if ( totalCount > 0) return ~~~~~
//      -- 먼저 ajax를 통해서 총 개수를 가져옴 -> 해당 숫자랑 같이 보냄
//-- 0일 때 진짜로 지울 건지 한 번 더 확인이 필요함! -> 너무 힘듦 그럼 그냥 바로 지우고 해당 결제수단이 삭제되었음을 알려줌
//-- 다시 alert을 통해서 지울 건지 확인을 받음 ㄹㅇ 지운다고 하면 그때 지움


  @DeleteMapping("/delete/{paymentMethodNo}")
  public ResponseEntity<?> deletePaymentMethod(@PathVariable int paymentMethodNo) {
    log.info(LOG_TITLE);
    log.info("deletePaymentMethod paymentMethodNo: {}", paymentMethodNo);

    Map<String, Object> resultMap = new HashMap<>();

//    이곳에 paymentMethodSelectOne 이 함수를 불러올 로직 완성해야 함
//    그 후에 service, dao, 각 impl들에도 추가해야 함

    paymentMethodService.paymentMethodDeleteOne(paymentMethodNo);

    return ResponseEntity.ok(paymentMethodNo);
  }

}

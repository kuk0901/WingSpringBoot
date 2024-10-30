package com.edu.wing.card.controller;

import com.edu.wing.card.domain.CardVo;
import com.edu.wing.card.service.CardService;
import com.edu.wing.cardBenefit.domain.CardBenefitVo;
import com.edu.wing.cardBenefit.service.CardBenefitService;
import com.edu.wing.sellingCard.service.SellingCardService;
import com.edu.wing.util.CardBenefitUtil;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/api/productManagement")
public class AdminApiCardController {
  private final Logger log = LoggerFactory.getLogger(AdminApiCardController.class);

  private final String STATUS = "status";
  private final String STATUS_SUCCESS = "success";
  private final String STATUS_FAIL = "failed";
  private final String STATUS_ERROR = "error";
  private final String ALERT_MSG = "alertMsg";

  @Autowired
  private CardService cardService;

  @Autowired
  private CardBenefitService cardBenefitService;

  @Autowired
  private SellingCardService sellingCardService;

  @GetMapping("/card-detail/{cardNo}")
  public ResponseEntity<?> productDetail(@PathVariable("cardNo") String cardNo, @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "all") String categoryName) {
    log.info("@GetMapping productDetail cardNo: {}, curPage: {}, categoryName: {}", cardNo, curPage, categoryName);

    CardVo cardVo = cardService.cardSelectOne(Integer.parseInt(cardNo));

    List<CardBenefitVo> allBenefits = cardBenefitService.cardBenefitSelectList();
    List<CardBenefitVo> benefitList =  CardBenefitUtil.filterBenefitsForCard(cardVo, allBenefits);

    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put(STATUS, STATUS_SUCCESS);
    resultMap.put("cardVo", cardVo);
    resultMap.put("benefitList", benefitList);
    resultMap.put("curPage", curPage);
    resultMap.put("categoryName", categoryName);

    return ResponseEntity.ok().body(resultMap);
  }

  @PostMapping("/card/insert")
  public ResponseEntity<?> insertCardAndBenefits(@RequestParam Map<String, String> formData,
                                                 @RequestParam(value = "originalFileName", required = false) MultipartFile file,
                                                 @RequestParam("benefits") String benefitsJson) {
    log.info("@PostMapping insertCardAndBenefits formData: {}", formData);

    Map<String, Object> resultMap = new HashMap<>();

    try {
      if (cardService.cardExist(formData.get("cardName"))) {
        resultMap.put(STATUS, STATUS_FAIL);
        resultMap.put(ALERT_MSG, "해당 이름의 카드가 이미 존재합니다. 동일 이름의 카드는 추가할 수 없습니다.");
        return ResponseEntity.status(HttpStatus.CONFLICT).body(resultMap);
      }

      int cardNo = cardService.insertCardAndBenefits(formData, file, benefitsJson);

      if (cardNo == 0) {
        resultMap.put(STATUS, STATUS_FAIL);
        resultMap.put(ALERT_MSG, "카드 등록 중 서버에서 오류가 발생했습니다. 카드 관리에서 작성한 카드 명의 카드가 이미 등록되었는지 확인 후 등록해 주세요.");
        return ResponseEntity.internalServerError().body(resultMap);
      }

      resultMap.put(STATUS, STATUS_SUCCESS);
      resultMap.put(ALERT_MSG, "추가하신 카드가 등록되었습니다. 상세 정보를 확인해 주세요.");
      resultMap.put("cardNo",cardNo);

      return ResponseEntity.status(HttpStatus.CREATED).body(resultMap); // 생성 성공!

    } catch (JsonSyntaxException e) {
      resultMap.put(STATUS, STATUS_ERROR);
      resultMap.put(ALERT_MSG, "잘못된 입력값입니다. 재입력 후 다시 등록해 주세요.");
      return ResponseEntity.badRequest().body(resultMap);

    } catch (RuntimeException e) {
      log.error("Error processing request: ", e);
      resultMap.put(STATUS, STATUS_ERROR);
      resultMap.put(ALERT_MSG, e.getMessage());
      return ResponseEntity.internalServerError().body(resultMap);

    } catch (Exception e) {
      log.error("Error processing request: ", e);
      resultMap.put(STATUS, STATUS_ERROR);
      resultMap.put(ALERT_MSG, "카드 등록 중 서버에서 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
      return ResponseEntity.internalServerError().body(resultMap);
    }
  }

  @DeleteMapping("/card/delete/{cardNo}")
  public ResponseEntity<?> deleteCardAndBenefits(@PathVariable int cardNo, @RequestParam(defaultValue = "1") int curPage, @RequestParam(defaultValue = "all") String categoryName) {
    log.info("@Delete card deleteCardAndBenefits cardNo: {}, curPage: {}, categoryName: {}", cardNo, curPage, categoryName);

    Map<String, Object> resultMap = new HashMap<>();
    resultMap.put("curPage", curPage);
    resultMap.put("categoryName", categoryName);

    // 현재 카드가 판매된 경우 삭제 불가하게 함
    int countActiveSellingCardsByCardNo = sellingCardService.countActiveSellingCardsByCardNo(cardNo);

    log.info("check countActiveSellingCardsByCardNo: {}", countActiveSellingCardsByCardNo);

    if (countActiveSellingCardsByCardNo > 0) {
      resultMap.put(STATUS, STATUS_FAIL);
      resultMap.put(ALERT_MSG, "현재 해당 카드가 " + countActiveSellingCardsByCardNo + "개 사용되고 있으므로 삭제할 수 없습니다.");
      return ResponseEntity.internalServerError().body(resultMap);
    }

    // 없는 경우 card 테이블의 IS_DELETED 칼럼의 값을 변경
    if (cardService.softDeleteCardAndVerify(cardNo)) {
      resultMap.put(STATUS, STATUS_SUCCESS);
      resultMap.put(ALERT_MSG, "요청하신 카드가 성공적으로 삭제되었습니다.");

      return ResponseEntity.ok().body(resultMap);
    }

    resultMap.put(STATUS, STATUS_FAIL);
    resultMap.put(ALERT_MSG, "카드 삭제 중 서버에서 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.");
    return ResponseEntity.internalServerError().body(resultMap);
  }
}

package com.edu.wing.util;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CardNumberGenerator {

  private static final String PREFIX = "574370"; // 고정값

  // 16자리 카드 번호 생성 함수
  public static String generateCardNumber() {
    String uuid = UUID.randomUUID().toString();

    // uuid에서 숫자만 추출
    String numbers = uuid.replace("-", "").replaceAll("[a-f]", "");

    // prefix(6자리) + 랜덤 숫자(9자리) = 15자리 (체크섬을 위해 한 자리 비움)
    String cardNumber = PREFIX + numbers.substring(0, 9);

    // Luhn 알고리즘을 사용한 체크섬 추가 후 반환
    return addLuhnChecksum(cardNumber);
  }

  // 유효한 형식이 되도록 마지막 자리를 추가
  private static String addLuhnChecksum(String number) {
    int sum = 0;
    boolean isEven = false;

    // 마지막 자리에서 시작해 첫 번째 자리까지 역순으로 진행
    for (int i = number.length() - 1; i >= 0; i--) {
      int digit = Character.getNumericValue(number.charAt(i));

      // 짝수번째 자리 처리
      if (isEven) {
        digit *= 2;
        if (digit > 9) {
          digit -= 9;
        }
      }

      sum += digit;
      isEven = !isEven;
    }

    // sum의 일의 자리만을 고려하여 체크섬 계산
    int checksum = (10 - (sum % 10)) % 10;
    return number + checksum;
  }
}

import { v4 as uuidv4 } from "https://jspm.dev/uuid";
import { formatString } from "../util/format.js";

// 16자리 카드 번호 생성  함수
export function generateCardNumber() {
  const prefix = "574370"; // 고정값
  const uuid = uuidv4();

  // uuid에서 숫자만 추출
  const numbers = uuid.replace(/-/g, "").replace(/[a-f]/g, "");

  // prefix(6자리) + 랜덤 숫자(9자리) = 15자리 (체크섬을 위해 한 자리 비움)
  const cardNumber = prefix + numbers.slice(0, 9);

  // Luhn 알고리즘을 사용한 체크섬 추가 후, 포맷팅
  return formatString(addLuhnChecksum(cardNumber));
}

// 유효한 형식이 되도록 마지막 자리를 추가
function addLuhnChecksum(number) {
  let sum = 0;
  let isEven = false; // 문자열의 길이가 짝수면 true, 홀수면 false

  // 마지막 자리에서 시작해 첫 번째 자리까지 역순으로 진행
  for (let i = number.length - 1; i >= 0; i--) {
    // 현재 위치의 문자를 가져와 10진수 정수로 변환
    let digit = parseInt(number.charAt(i), 10);

    // 짝수번째 자리 처리
    // 숫자를 2배로 만듦, 결과가 9보다 크면 9를 뺌
    if (isEven) {
      digit *= 2;
      if (digit > 9) {
        digit -= 9;
      }
    }

    // 홀수번째 자리는 바로 더함
    sum += digit;
    isEven = !isEven;
  }

  // sum의 일의 자리만을 고려, sum이 어떤 값이든 결과는 항상 0 ~ 9 사이
  // sum % 10 = 0일 때 (10 - 0) = 10이 되는 경우를 처리 => 결과 항상 0 ~ 9 사이
  const checksum = (10 - (sum % 10)) % 10;
  return number + checksum;

}

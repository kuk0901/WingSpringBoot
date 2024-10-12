export function formatNumber(input) {
  // 숫자와 콤마만 남기고 모든 문자 제거
  let value = input.value.replace(/[^\d,]/g, "");

  // 숫자만 추출
  let numbers = value.replace(/,/g, "");

  // 패턴에 맞는지 확인 (1-9로 시작하는 숫자)
  if (!/^[1-9]\d*$/.test(numbers)) {
    input.value = "";
    return;
  }

  // 3자리마다 콤마 추가
  input.value = numbers.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

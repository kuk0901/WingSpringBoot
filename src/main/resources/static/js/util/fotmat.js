/**
 * 사용자의 연봉, 월급을 포맷팅
 * @param {HTMLInputElement} input
 * @returns {void}
 */
export function formatNumber(input) {
  // 숫자를 제외한 모든 문자 제거 (콤마와 하이픈 포함)
  const numbers = input.value.replace(/\D/g, "");

  const regex = /^[1-9]\d*$/;

  // 패턴에 맞는지 확인 (1-9로 시작하는 숫자)
  if (!regex.test(numbers)) {
    input.value = "";
    return;
  }

  // 3자리마다 콤마 추가
  input.value = numbers.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

/**
 * 사용자의 전화번호를 포맷팅
 * @param {HTMLInputElement} input
 * @returns {void}
 */
export function formatPhoneNumber(input) {
  // 숫자를 제외한 모든 문자 제거 (콤마와 하이픈 포함)
  let number = input.value.replace(/\D/g, '');

  // 패턴에 맞는지 확인
  const regex = /^01[016789][0-9]{3,4}[0-9]{4}$/;

  if (!regex.test(number)) {
    input.value = "";
    return;
  }

  if (number.length === 10) {
    // 010-123-1234 형식
    number = number.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
  } else {
    // 010-1234-1234 형식
    number = number.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
  }

  input.value = number;
}
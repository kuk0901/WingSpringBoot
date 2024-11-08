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

/**
 * card 요약 혜택을 태그로 포맷팅
 * @param {ajax.data} summBenefit
 * @returns {string}
 */
export function formatCardBenefitSummaryToTags(summBenefit) {
  let splitSummBenefit = summBenefit.split("^*^");

  return splitSummBenefit
      .filter(split => split.trim() !== '')
      .map(split => `<li class="li--ui">${split}</li>`).join('').trim();
}

/**
 * card 요약 혜택을 태그로 포맷팅
 * @param {string} value
 * @returns {int}
 */
export function unformatNumber(value) {
  // 콤마 제거 후 숫자로 변환
  return Number(value.replace(/,/g, ''));
}

// 4자리씩 그룹으로 묶은 후 "-"" 추가용 함수(카드)
export function formatString(input) {
  return input.replace(/(\w{4})(?=\w)/g, "$1-");
}

/**
 * 가계부에서 금액을 포맷팅
 * @param {number} paymentAmount
 * @returns {string}
 */
export function formatPaymentAmountNumber(paymentAmount) {
  // 절대값으로 변환 + 3자리마다 콤마 추가
  return Math.abs(Math.floor(paymentAmount)).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

/**
 * 회원 마이페이지에서 날짜 데이터
 * @param {string} dateString
 * @returns {string}
 */
export function formatDate(dateString) {
  if (!dateString) return '해당 사항 없음'; // null 또는 undefined일 경우
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = date.getMonth() + 1; // getMonth는 0부터 시작
  const day = date.getDate();
  return `${year}년 ${month}월 ${day}일`;
}

/**
 * 마이페이지 연봉, 월급 포맷팅
 * @param {string} dateString
 * @returns {string}
 */
export function unFormatNumberString(value) {
  return parseInt(value.replace(/만원/g, '').replace(/,/g, '').trim());
}
























const CUSTOM_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

export function encodeCustom(input) {
  let result = "";
  for (let i = 0; i < input.length; i++) {
    let char = input.charCodeAt(i);
    result += CUSTOM_CHARS[char % 62]; // ASCII 값을 62로 나눈 나머지로 인덱스 결정
    result += CUSTOM_CHARS[Math.floor(char / 62)]; // ASCII 값을 62로 나눈 몫으로 인덱스 결정
  }
  return result;
}

export function decodeCustom(input) {
  let result = "";
  for (let i = 0; i < input.length; i += 2) {
    let char1Index = CUSTOM_CHARS.indexOf(input[i]); // 첫 번째 문자 인덱스 찾기
    let char2Index = CUSTOM_CHARS.indexOf(input[i + 1]); // 두 번째 문자 인덱스 찾기
    let charCode = char1Index + char2Index * 62; // 원래의 ASCII 값 복원
    result += String.fromCharCode(charCode); // ASCII 값을 문자로 변환하여 결과에 추가
  }
  return result;
}
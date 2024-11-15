package com.edu.wing.util;

public class CustomEncoding {
  // 인코딩 및 디코딩에 사용되는 문자 집합
  private static final String CUSTOM_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  /**
   * 입력 문자열을 인코딩하는 메서드.
   * @param input 인코딩할 문자열
   * @return 인코딩된 문자열
   */
  public static String encode(String input) {
    StringBuilder result = new StringBuilder(); // 결과 문자열을 저장할 StringBuilder

    // 입력 문자열의 각 문자를 반복
    for (char c : input.toCharArray()) {
      int charCode = c; // 문자의 ASCII 값을 정수로 변환

      // ASCII 값을 62로 나눈 나머지를 인덱스로 사용하여 CUSTOM_CHARS에서 문자 선택
      result.append(CUSTOM_CHARS.charAt(charCode % 62));
      // ASCII 값을 62로 나눈 몫을 인덱스로 사용하여 CUSTOM_CHARS에서 문자 선택
      result.append(CUSTOM_CHARS.charAt(charCode / 62));
    }
    return result.toString(); // 인코딩된 문자열 반환
  }

  /**
   * 인코딩된 문자열을 원래 문자열로 복원하는 메서드.
   * @param input 복원할 인코딩된 문자열
   * @return 원래의 문자열
   */
  public static String decode(String input) {
    StringBuilder result = new StringBuilder(); // 결과 문자열을 저장할 StringBuilder

    // 입력 문자열의 각 두 문자를 반복
    for (int i = 0; i < input.length(); i += 2) {
      // 첫 번째 문자에 대한 인덱스 찾기
      int char1Index = CUSTOM_CHARS.indexOf(input.charAt(i));
      // 두 번째 문자에 대한 인덱스 찾기
      int char2Index = CUSTOM_CHARS.indexOf(input.charAt(i + 1));
      // 원래의 ASCII 값을 계산 (char1Index + char2Index * 62)
      int charCode = char1Index + char2Index * 62;
      // ASCII 값을 문자로 변환하여 결과에 추가
      result.append((char) charCode);
    }
    return result.toString(); // 복원된 문자열 반환
  }
}
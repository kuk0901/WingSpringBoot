<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Find Account</title>
  <link rel="stylesheet" href="/css/auth/findAccount.css" />
  <script defer type="module" src="/js/auth/findAccount.js"></script>
</head>
<body>

  <jsp:include page="/WEB-INF/views/jsp/components/Header.jsp" />

  <!-- form container -->
  <div class="form-container">
    <div class="subject-container bg__red">
      <h2 class="subject text__white">계정 찾기</h2>
    </div>

    <div class="error-container text__center">
      <div id="errorMsg" class="text__error">존재하지 않는 회원입니다.</div>
    </div>

    <form id="findAccountForm" class="find-account-form">
      <div class="account-container">
        <div class="label-container">
          <label for="phone" class="text__white">휴대폰 번호</label>
        </div>
        <div class="input-container">
          <input
              type="tel"
              id="phone"
              name="phone"
              required
              pattern="^01[016789]-?[0-9]{3,4}-?[0-9]{4}$"
              placeholder="ex) 01012345678"
              class="signin--input__m"
              maxlength="14"
          />
        </div>
      </div>

      <div class="account-container">
        <div class="label-container">
          <label for="pwd" class="text__white">패스워드</label>
        </div>
        <div class="input-container">
          <input
              id="pwd"
              name="pwd"
              type="password"
              placeholder="비밀번호를 작성해 주세요."
              required
              pattern="^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{8,21}$"
              class="signin--input__m"
              autocomplete="current-password"
          />
        </div>
      </div>

      <div class="submit-container">
        <input id="submit" type="submit" class="btn btn__sign text__white" value="Find" />
      </div>
    </form>

    <div class="sub-container">
      <div class="find-container">
        <a href="/" class="text__white">로그인</a>
      </div>
      <div class="find-container">
        <a href="${pageContext.request.contextPath}/auth/find/password" class="text__white">비밀번호 찾기</a>
      </div>
      <div class="signup-container">
        <a href="${pageContext.request.contextPath}/auth/signup" class="text__white">회원가입</a>
      </div>
    </div>
  </div>
</body>
</html>

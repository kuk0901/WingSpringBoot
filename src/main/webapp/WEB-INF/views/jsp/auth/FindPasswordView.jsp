<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>비밀번호 찾기</title>
  <link rel="stylesheet" href="/css/auth/findPassword.css" />
  <script defer type="module" src="/js/auth/findPassword.js"></script>
</head>
<body>

  <jsp:include page="/WEB-INF/views/jsp/components/Header.jsp" />

  <!-- form container -->
  <div id="formContainer" class="form-container">
    <div class="subject-container bg__red">
      <h2 class="subject text__white">비밀번호 찾기</h2>
    </div>

    <div class="error-container text__center">
      <div id="errorMsg" class="text__error">존재하지 않는 회원입니다.</div>
    </div>

    <form id="findPwdForm" class="find-password-form">
      <div class="password-container">
        <div class="label-container">
          <label for="userName" class="text__white">성명</label>
        </div>
        <div class="input-container">
          <input
              id="userName"
              name="userName"
              type="text"
              required
              pattern="[가-힣]{2,7}"
              placeholder="ex) 홍길동"
          />
        </div>
      </div>

      <div class="password-container">
        <div class="label-container">
          <label for="email" class="text__white">이메일</label>
        </div>
        <div class="input-container">
          <input
              id="email"
              name="email"
              type="email"
              required
              pattern="^(?=.{6,36}$)[a-z0-9_]+@[a-z0-9.\-]+\.[a-z]{2,}$"
              placeholder="이메일을 작성해 주세요."
              class="signin--input__m"
              autocomplete="username"
          />
        </div>
      </div>

      <div class="submit-container">
        <input type="submit" class="btn btn__sign text__white" value="Find" />
      </div>
    </form>

    <div class="sub-container">
      <div class="find-container">
        <a href="/" class="text__white">로그인</a>
      </div>
      <div class="find-container">
        <a href="${pageContext.request.contextPath}/auth/find/account" class="text__white">계정 찾기</a>
      </div>
      <div class="signup-container">
        <a href="${pageContext.request.contextPath}/auth/signup" class="text__white">회원가입</a>
      </div>
    </div>
  </div>
</body>
</html>

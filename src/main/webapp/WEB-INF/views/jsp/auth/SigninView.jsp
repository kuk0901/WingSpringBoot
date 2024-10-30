<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>SignIn</title>
  <link rel="stylesheet" href="/css/auth/signin.css" />
  <script defer type="module" src="/js/auth/signin.js"></script>
</head>
<body>
  <jsp:include page="/WEB-INF/views/jsp/components/toast.jsp">
    <jsp:param value="${alertMsg}" name="alertMsg" />
  </jsp:include>

  <jsp:include page="/WEB-INF/views/jsp/components/Header.jsp" />

  <!-- form container -->
  <div class="form-container">
    <div class="subject-container bg__red">
      <h2 class="subject text__white">슬기롭게 소비 생활하기</h2>
    </div>

    <form id="signinForm" class="signin-form">
      <div class="signin-container">
        <div class="label-container">
          <label for="email" class="text__white">이메일</label>
        </div>
        <div class="input-container email-check-porision">
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

      <div class="signin-container">
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
        <input type="submit" class="btn btn__sign text__white" value="SignIn" />
      </div>
    </form>

    <div class="sub-container">
      <div class="find-container">
        <a href="#" class="text__white">계정 찾기</a>
      </div>
      <div class="find-container">
        <a href="#" class="text__white">비밀번호 찾기</a>
      </div>
      <div class="signup-container">
        <a href="auth/signup" class="text__white">회원가입</a>
      </div>
    </div>
  </div>
</body>
</html>

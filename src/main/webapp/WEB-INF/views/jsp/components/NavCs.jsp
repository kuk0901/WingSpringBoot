<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Member Nav -->
<nav
    class="nav member-nav"
    role="navigation"
    aria-label="사용자 메뉴"
    id="memberNav"
>
  <ul class="btn-container logo-container">
    <li>
      <a href="${pageContext.request.contextPath}/member/main" id="logoLink">
        <img src="/img/logo.png" alt="WING_ logo" class="logo" />
      </a>
    </li>
  </ul>

  <ul class="site-title-container">
    <li class="site-title text__center text__semibold">스마트한 가계부 서비스</li>
  </ul>

  <ul class="btn-container user-container">
    <c:if test="${not empty sessionScope.member}">
      <li>
        <a href="${pageContext.request.contextPath}/member/user/myPage" id="userName" class="btn btn--member__my text__semibold move-page" aria-label="사용자 프로필">
            ${sessionScope.member.userName}님
        </a>
      </li>
    </c:if>

    <c:if test="${empty sessionScope.member}">
      <li>
        <a href="#" class="btn btn--member__my text__semibold move-page" aria-label="사용자 프로필">
          @@@님
        </a>
      </li>
    </c:if>
  </ul>

  <span class="white-line"></span>

  <ul class="btn-container main-content" aria-label="주요 메뉴">
    <li>
      <a href="#" class="btn btn__yellow text__semibold move-page" aria-current="page">
        공지사항
      </a>
    </li>
    <li>
      <a href="#" class="btn btn__yellow text__semibold move-page">1대1 문의</a>
    </li>
  </ul>

  <ul class="btn-container signout-container member-signout">
    <li>
      <a href="/auth/signout" class="btn btn__signout text__black bg__white"><img class="signout__img" src="/img/lock_open.svg" alt="open"/>Sign Out</a>
    </li>
  </ul>
</nav>


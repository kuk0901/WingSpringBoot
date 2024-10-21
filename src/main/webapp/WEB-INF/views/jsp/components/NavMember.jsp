<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- Member Nav -->
<nav
    class="member-nav"
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

  <ul class="btn-container user-container">
    <c:if test="${not empty sessionScope.member}">
      <li>
        <a href="#" id="userName" class="btn btn--member__my btn__p text__semibold" aria-label="사용자 프로필">
            ${sessionScope.member.name}님
        </a>
      </li>
    </c:if>

    <c:if test="${empty sessionScope.member}">
      <li>
        <a href="#" class="btn btn--member__my btn__p text__semibold" aria-label="사용자 프로필">
          @@@님
        </a>
      </li>
    </c:if>
  </ul>

  <span class="white-line"></span>

  <ul class="btn-container" aria-label="주요 메뉴">
    <li>
      <a href="#" class="btn btn__yellow btn__p" aria-current="page"
      >가계부</a
      >
    </li>
    <li>
      <a href="#" class="btn btn__yellow btn__p">카드 종류</a>
    </li>
  </ul>

  <span class="white-line"></span>

  <ul class="btn-container" aria-label="콘텐츠 메뉴">
    <li><a href="#" class="btn btn__yellow btn__p">게시판</a></li>
  </ul>

  <ul class="btn-container signout-container member-signout">
    <li>
      <a href="#" class="btn btn__signout text__black bg-white">Sign Out</a>
    </li>
  </ul>
</nav>


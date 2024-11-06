<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Admin Nav -->
<nav
    class="nav admin-nav"
    role="navigation"
    aria-label="관리자 메뉴"
    id="adminNav"
>
  <div class="info-container">
    <ul class="btn-container logo-container">
      <li>
        <a href="${pageContext.request.contextPath}/admin/salesDashboard/list" id="logoLink">
          <img src="${pageContext.request.contextPath}/img/logo.png" alt="WING_ logo" class="logo" />
        </a>
      </li>
    </ul>

    <ul class="site-title-container">
      <li class="site-title text__center text__semibold">스마트한 가계부 서비스</li>
    </ul>

    <ul class="btn-container user-container">
      <c:if test="${not empty sessionScope.member}">
        <li>
          <a href="/admin/user/myPage/${sessionScope.member.memberNo}" id="userName" class="btn btn--admin__my text__semibold move-page" aria-label="사용자 프로필">
            ${sessionScope.member.userName}님
          </a>
        </li>
      </c:if>

      <c:if test="${empty sessionScope.member}">
        <li>
          <a href="#" class="btn btn--admin__my text__semibold move-page" aria-label="사용자 프로필">
            @@@님
          </a>
        </li>
      </c:if>
    </ul>
  </div>

  <ul class="btn-container" aria-label="주요 관리 메뉴">
    <li>
      <a href="${pageContext.request.contextPath}/admin/dashboard/card" class="btn btn__blue move-page" aria-current="page"
      >대시보드</a
      >
    </li>
    <li>
      <a href="${pageContext.request.contextPath}/admin/salesDashboard/list" class="btn btn__blue move-page">판매 카드 현황</a>
    </li>
    <li>
      <a href="${pageContext.request.contextPath}/admin/productManagement/list" class="btn btn__blue move-page">카드 관리</a>
    </li>
  </ul>

  <span class="white-line"></span>

  <ul class="btn-container" aria-label="재무 관리 메뉴">
    <li><a href="${pageContext.request.contextPath}/admin/accountBook/list" class="btn btn__blue move-page">가계부 관리</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/category/list" class="btn btn__blue move-page">카테고리 관리</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/paymentMethod/list" class="btn btn__blue move-page">결제수단 관리</a></li>
  </ul>

  <span class="white-line"></span>

  <ul class="btn-container" aria-label="사용자 및 콘텐츠 관리 메뉴">
    <li><a href="${pageContext.request.contextPath}/admin/member/list" class="btn btn__blue move-page">회원 관리</a></li>
    <li><a href="#" class="btn btn__blue move-page">게시판 관리</a></li>
    <li><button id="csControlBtn" class="btn btn__blue move-page">고객센터 관리</button></li>
    <li id="inquiry" class="cs-line active"><a href="${pageContext.request.contextPath}/admin/cs/inquiry/list" class="btn btn__blue cs--inquiry move-page">1대1 문의</a></li>
  </ul>

  <ul class="btn-container signout-container admin-signout">
    <li>
      <a href="/auth/signout" class="btn btn__signout text__black bg__white"><img class="signout__img" src="/img/lock_open.svg" alt="open"/>Sign Out</a>
    </li>
  </ul>
</nav>


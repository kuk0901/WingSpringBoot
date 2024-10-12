<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!-- Admin Nav -->
<nav
    class="admin-nav"
    role="navigation"
    aria-label="관리자 메뉴"
    id="adminNav"
>
  <ul class="btn-container logo-container">
    <li>
      <a href="${pageContext.request.contextPath}/admin/main">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="WING_ logo" class="logo" />
      </a>
    </li>
  </ul>

  <ul class="btn-container user-container">
    <li>
      <a
          href="#"
          class="btn btn--admin__my btn__p"
          aria-label="사용자 프로필"
      >@@@님</a
      >
    </li>
  </ul>

  <ul class="btn-container" aria-label="주요 관리 메뉴">
    <li>
      <a href="#" class="btn btn__blue btn__p" aria-current="page"
      >대시보드</a
      >
    </li>
    <li>
      <a href="#" class="btn btn__blue btn__p">판매 카드 현황</a>
    </li>
    <li>
      <a href="#" class="btn btn__blue btn__p">카드 관리</a>
    </li>
  </ul>

  <span class="white-line"></span>

  <ul class="btn-container" aria-label="재무 관리 메뉴">
    <li><a href="#" class="btn btn__blue btn__p">가계부 관리</a></li>
    <li><a href="#" class="btn btn__blue btn__p">카테고리 관리</a></li>
    <li><a href="#" class="btn btn__blue btn__p">결제수단 관리</a></li>
  </ul>

  <span class="white-line"></span>

  <ul class="btn-container" aria-label="사용자 및 콘텐츠 관리 메뉴">
    <li><a href="#" class="btn btn__blue btn__p">회원 관리</a></li>
    <li><a href="#" class="btn btn__blue btn__p">게시판 관리</a></li>
    <li><a href="#" class="btn btn__blue btn__p">고객센터 관리</a></li>
  </ul>

  <ul class="btn-container signout-container admin-signout">
    <li>
      <a href="#" class="btn btn__signout text__black bg-white">Sign Out</a>
    </li>
  </ul>
</nav>


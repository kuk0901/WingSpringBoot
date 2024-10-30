<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>회원관리</title>
  <script src="https://code.jquery.com/jquery-3.7.0.js"
          integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
          crossorigin="anonymous">
  </script>
  <script defer type="module" src="/js/member/user/userMyPage.js"></script>
  <script type="text/javascript">


  </script>
  <link rel="stylesheet" href="/css/member/user/userMyPage.css">
  <jsp:include page="/WEB-INF/views/jsp/common/common.jsp"/>
</head>


<body>
<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavMember.jsp"/>

  <section id="memberSection">
    <div id="content">
      <div class="title-container">
        <div class="title btn__blue text__white">
          마이 페이지
        </div>
      </div>

      <main class="main-container">
        <form id="myPageForm">
          <div id="memberDetailContainer" class="update_container">
            <div class="detail_title_container">정보 수정</div>
            <div class="detail_first_second_wrapper">
              <div class="detail_first_container detail_container">
                <div>
                  <label>이메일</label>
                  <input type="email" id="email" name="email"
                         pattern="^(?=.{6,36}$)[a-z0-9_]+@[a-z0-9.\-]+\.[a-z]{2,}$"
                         autocomplete="off"/>
                  <span id="emailError"></span>
                </div>
                <div>
                  <label>이름</label>
                  <input type="text" id="Name" name="Name" />
                </div>
                <div>
                  <label>패스워드</label>
                  <input type="password" id="password" name="password"
                         pattern="^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{8,21}$"
                         autocomplete="off"/>
                </div>
                <div>
                  <label>패스워드 확인</label>
                  <input type="password" id="confirmPassword" name="confirmPassword"
                         pattern="^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{8,21}$"
                         autocomplete="off"/>
                  <span id="pwdError"></span>
                </div>
                <div>
                  <label>핸드폰 번호</label>
                  <input type="text" id="phone" name="phone" />
                </div>
                <div>
                  <label>가입일</label>
                  <input type="text" id="creDate" name="creDate" readonly />
                </div>
              </div>
              <div class="detail_second_container detail_container">
                <div>
                  <label>연봉</label>
                  <input type="text" id="yearlySalary" name="yearlySalary" />
                </div>
                <div>
                  <label>월급</label>
                  <input type="text" id="monthlySalary" name="monthlySalary" />
                </div>
              </div>
            </div>
          </div>
          <div class="button-container color bg">
            <button id="updateButton" class="btn btn__generate">정보 수정</button>
          </div>
        </form>
  <%--      나중에 c:choose로 설정할것 보유시->그대로//미보유시 N->따로 js처리--%>
        <div id="card-container" class="card-container">
          <div class="card-title text__semibold">보유 카드</div>
          <div class="card-header text__semibold">
            <div class="header-item box__s">카드 명</div>
            <div class="header-item box__l">혜택 요약</div>
            <div class="header-item box__date">등록 날짜</div>
            <div class="header-item box__no">카드 번호</div>
            <div class="header-item box__btn">비고
            </div>
          </div>
        </div>
      </main> <!-- main 태그 닫기 -->
    </div> <!-- content 닫기 -->

    <jsp:include page="/WEB-INF/views/jsp/components/Footer.jsp"/>
  </section>
  <input type="hidden" id="memberNo" value="${sessionScope.member.memberNo}" />
</section>
<jsp:include page="/WEB-INF/views/jsp/components/scrollToTop.jsp" />

</body>
</html>
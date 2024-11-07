<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/views/jsp/common/common.jsp"/>
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
  <link rel="stylesheet" href="/css/member/user/userMyPage.css">
</head>


<body>
<jsp:include page="/WEB-INF/views/jsp/components/toast.jsp">
  <jsp:param value="${alertMsg}" name="alertMsg" />
</jsp:include>

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
            <div id="quitApplyNotice" style="display: none">
              <!-- 메시지가 여기에 표시됩니다 -->
            </div>
            <div class="detail_first_second_wrapper">
              <div class="detail_first_container detail_container">
                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="email">이메일</label>
                  </div>
                  <div class="input-container">
                  <input type="email" id="email" name="email"
                         pattern="^(?=.{6,36}$)[a-z0-9_]+@[a-z0-9.\-]+\.[a-z]{2,}$"
                         autocomplete="off"/>
                  <span id="emailError"></span>
                  </div>
                </div>
                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="Name">이름</label>
                  </div>
                  <div class="input-container">
                    <input type="text" id="Name" name="Name" />
                    <span id="userNameError"></span>
                  </div>
                </div>
                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="password">패스워드</label>
                  </div>
                  <div class="input-container">
                    <input type="password" id="password" name="password"
                         pattern="^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{8,21}$"
                         autocomplete="off"/>
                    <span id="pwdError"></span>
                  </div>
                </div>
                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="confirmPassword">패스워드 확인</label>
                  </div>
                  <div class="input-container">
                    <input type="password" id="confirmPassword" name="confirmPassword"
                           pattern="^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{8,21}$"
                           autocomplete="off"/>
                    <span id="pwdCheckError"></span>
                  </div>
                </div>
                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="phone">전화번호</label>
                  </div>
                  <div class="input-container">
                    <input type="text" id="phone" name="phone" />
                    <span id="phoneError"></span>
                  </div>
                </div>
                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="creDate">가입일</label>
                  </div>
                  <div class="input-container">
                    <input type="text" id="creDate" name="creDate" readonly />
                  </div>
                </div>
              </div>
              <div class="detail_second_container detail_container">
                <div class="user-info-container one-line">
                  <div class="label-container text-right">
                    <label for="yearlySalary" class="box__s">연봉</label>
                  </div>
                  <div class="input-container">
                    <input type="text" id="yearlySalary" name="yearlySalary" class="box__m" />
                    <span>만원</span>
                    <span id="salaryError"></span>
                  </div>
                </div>
                <div class="user-info-container one-line">
                  <div class="label-container text-right">
                    <label for="monthlySalary" class="box__s">월급</label>
                  </div>
                  <div class="input-container">
                    <input type="text" id="monthlySalary" name="monthlySalary" class="box__m" />
                    <span>만원</span>
                    <span id="payError"></span>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="two-btn-container color bg">
            <button id="updateButton" class="btn btn__generate">정보 수정</button>
            <button id="quitMemberButton" class="btn btn__generate">탈퇴 신청</button>
          </div>
        </form>
  <%--      나중에 c:choose로 설정할것 보유시->그대로//미보유시 N->따로 js처리--%>
        <div id="card-container" class="card-container" >

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

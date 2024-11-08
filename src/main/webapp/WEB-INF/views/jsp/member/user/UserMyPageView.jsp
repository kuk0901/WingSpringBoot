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
                         autocomplete="off"
                         value="${memberVo.email}" />
                  <span id="emailError"></span>
                  </div>
                </div>
                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="Name">이름</label>
                  </div>
                  <div class="input-container">
                    <input type="text" id="Name" name="Name"
                           value="${memberVo.userName}" />

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
                         autocomplete="off"
                           value="${memberVo.pwd}" />
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
                           autocomplete="off"
                   value="${memberVo.pwd}" />
                           autocomplete="off"/>
                    <span id="pwdCheckError"></span>
                  </div>
                </div>
                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="phone">전화번호</label>
                  </div>
                  <div class="input-container">

                    <input type="text" id="phone" name="phone" value="${memberVo.phone}"/>
                    <span id="phoneError"></span>
                  </div>
                </div>
                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="creDate">가입일</label>
                  </div>
                  <div class="input-container">
                    <input type="text" id="creDate" name="creDate" readonly
                           value="<fmt:formatDate value='${memberVo.creDate}' pattern='yyyy-MM-dd' />" />
                  </div>
                </div>
              </div>
              <div class="detail_second_container detail_container">
                <div class="user-info-container one-line">
                  <div class="label-container text-right">
                    <label for="yearlySalary" class="box__s">연봉</label>
                  </div>
                  <div class="input-container">
                    <input type="text" id="yearlySalary" name="yearlySalary" class="box__m"
                           value="${memberVo.yearlySalary}" />
                    <span>만원</span>
                    <span id="salaryError"></span>
                  </div>
                </div>
                <div class="user-info-container one-line">
                  <div class="label-container text-right">
                    <label for="monthlySalary" class="box__s">월급</label>
                  </div>
                  <div class="input-container">
                    <input type="text" id="monthlySalary" name="monthlySalary" class="box__m"
                           value="${memberVo.monthlySalary}"/>
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
<c:choose>
  <c:when test="${not empty sellingCard}">
        <div id="card-container" class="card-container" >
          <div class="card-title text__semibold">보유 카드</div>
          <div class="card-header">
            <div class="header-item box__s text__semibold">카드 명</div>
            <div class="header-item box__m text__semibold">혜택 요약</div>
            <div class="header-item box__m text__semibold">등록 날짜</div>
            <div class="header-item box__l text__semibold">카드 번호</div>
            <div class="header-item box__m text__semibold">비고</div>
          </div>

          <div class="bg__white">
            <div class="list-container list-content one-line">
              <div class="list-item box__s text__center">
                <div class="card-name">${sellingCard.CARDNAME}</div>
                <div class="img-container">
                  <img class="card--img" src="/img/card/${sellingCard.STOREDFILENAME}" alt="${sellingCard.CARDNAME}" />
                </div>
              </div>
              <div class="list-item box__m" id="benefit-container">
                <c:forEach var="benefit" items="${benefits}">
                  <div class="card-benefit">
                    <p>
                        ${benefit.cardBenefitDetail} ${benefit.cardPercentage}% ${benefit.cardBenefitDivision}
                    </p>
                  </div>
                </c:forEach>
              </div>

              <div class="list-item box__m text__center">
                <c:out value="${fn:substring(sellingCard.SELLINGDATE, 0, 10)}" />
              </div>
              <div class="list-item box__l text__center">${sellingCard.MEMBERCARDNO}</div>
              <div class="list-item box__btn box__m">
                <div id="button-container" class="btn-container text__center one-line">
                  <button id="terminationRequestButton" class="btn btn__generate btn--vertical">해지 신청</button>
                  <button id="cardUseDetail" class="btn btn__generate btn--vertical">상세 내역</button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <input id='sellingCardNo' type='hidden' value='${sellingCard.SELLINGCARDNO}
  </c:when>
</c:choose>
      </main> <!-- main 태그 닫기 -->
    </div> <!-- content 닫기 -->

    <jsp:include page="/WEB-INF/views/jsp/components/Footer.jsp"/>
  </section>
  <input type="hidden" id="memberNo" value="${sessionScope.member.memberNo}" />
</section>
<jsp:include page="/WEB-INF/views/jsp/components/scrollToTop.jsp" />

</body>
</html>

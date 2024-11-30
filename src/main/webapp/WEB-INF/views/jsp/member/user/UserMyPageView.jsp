<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>마이페이지 </title>
  <link rel="stylesheet" href="/css/member/card/memberProduct.css">
  <link rel="stylesheet" href="/css/member/user/userMyPage.css">

  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script defer type="module" src="/js/member/user/userMyPage.js"></script>
  <script defer type="module" src="/js/member/card/AjaxRecommendedCardDetail.js"></script>
</head>

<body>
<jsp:include page="/WEB-INF/views/jsp/components/toast.jsp">
  <jsp:param value="${alertMsg}" name="alertMsg"/>
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
                           value="${memberVo.email}"/>
                    <span id="emailError"></span>
                  </div>
                </div>

                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="memberName">이름</label>
                  </div>
                  <div class="input-container">
                    <input type="text" id="memberName" name="userName"
                           value="${memberVo.userName}"/>

                    <span id="userNameError"></span>
                  </div>
                </div>

                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="pwd">패스워드</label>
                  </div>
                  <div class="input-container">
                    <input type="password" id="pwd" name="pwd"
                           pattern="^(?=.*\d)(?=.*[a-zA-Z])[a-zA-Z\d]{8,21}$"
                           autocomplete="off"
                           value="${memberVo.pwd}"/>

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
                           value="${memberVo.pwd}"/>
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
                           value="<fmt:formatDate value='${memberVo.creDate}' pattern='yyyy-MM-dd' />"/>
                  </div>
                </div>
              </div>

              <div class="detail_second_container detail_container">
                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="yearlySalary" class="box__xs">연봉</label>
                  </div>
                  <div class="input-container">
                    <input type="text" id="yearlySalary" name="yearlySalary" class="box__m"
                           value="${memberVo.yearlySalary}"/>
                    <span>만원</span>
                    <span id="salaryError"></span>
                  </div>
                </div>

                <div class="user-info-container one-line">
                  <div class="label-container">
                    <label for="monthlySalary" class="box__xs">월급</label>
                  </div>
                  <div class="input-container">
                    <input type="text" id="monthlySalary" name="monthlySalary" class="box__m"
                           value="${memberVo.monthlySalary}"/>
                    <span>만원</span>
                    <span id="payError"></span>
                  </div>
                </div>

                <div class="user-info-container non-one-line user-percentage-container">
                  <div class="percentage-container">
                    <c:choose>
                      <c:when test="${monthlySalaryPer == 0}">
                        <div class="btn__red text__semibold percentage">이번 달 소비가 <span class="text__primary">동 월급 그룹 내에서 가장 적습니다!!</span></div>
                      </c:when>
                      <c:otherwise>
                        <div class="btn__blue text__semibold percentage">이번 달 나의 소비는 <span class="text__primary">동 월급 그룹 내 하위 ${monthlySalaryPer}%!</span></div>
                      </c:otherwise>
                    </c:choose>
                  </div>
                  <div class="percentage-container">
                    <c:choose>
                      <c:when test="${yearSalaryPer == 0}">
                        <div class="btn__red text__semibold percentage">이번 달 소비가 <span class="text__primary">동 연봉 그룹 내에서 가장 적습니다!!</span></div>
                      </c:when>
                      <c:otherwise>
                        <div class="btn__blue text__semibold percentage">이번 달 나의 소비는 <span class="text__primary">동 연봉 그룹 내 하위 ${yearSalaryPer}%!</span></div>
                      </c:otherwise>
                    </c:choose>
                  </div>
              </div>
              </div>
            </div>
          </div>

          <div class="two-btn-container color bg">
            <button  id="updateButton" class="btn btn__generate">정보 수정</button>
            <button type="button" id="quitMemberButton" class="btn btn__generate">탈퇴 신청</button>
          </div>
        </form>

        <c:choose>
          <c:when test="${not empty sellingCard}">
            <div id="card-container" class="card-container">
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
                      <img class="card--img" src="/img/card/${sellingCard.STOREDFILENAME}" alt="${sellingCard.CARDNAME}"/>
                    </div>
                  </div>
                  <div class="list-item box__m" id="benefit-container">
                    <c:forEach var="cardBenefitVo" items="${cardBenefitVoList}">
                      <div class="card-benefit">
                        <p>
                            ${cardBenefitVo.cardBenefitDetail} ${cardBenefitVo.cardPercentage}% ${cardBenefitVo.cardBenefitDivision}
                        </p>
                      </div>
                    </c:forEach>
                  </div>

                  <div class="list-item box__m text__center">
                    <c:out value="${fn:substring(sellingCard.SELLINGDATE, 0, 10)}"/>
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
          </c:when>
          <c:otherwise>
            <c:if test="${recommendedCard.CARDNO > 0}">
              <div class="btn__blue recommend-card-container one-line">
                <div class="reco-card-info">
                  <div class="reco-card-info--text">
                    <c:choose>
                      <c:when test="${fn:endsWith(fn:trim(recommendedCard.CATEGORYNAME), '비')}">
                        <div class="reco-card-info--title">3개월 연속으로 ${recommendedCard.CATEGORYNAME}에 가장 많이 소비했습니다!</div>
                      </c:when>
                      <c:otherwise>
                        <div class="reco-card-info--title">3개월 연속으로 ${recommendedCard.CATEGORYNAME}비에 가장 많이 소비했습니다!</div>
                      </c:otherwise>
                    </c:choose>
                    <div class="reco-card-info--text reco-card-info--guide">
                      ${recommendedCard.CARDNAME} 카드를 신청해보시는 건 어떠신가요?
                    </div>
                    <div class="reco-card-info--text reco-card-info--benefits">
                      <c:forEach var="cardBenefit" items="${recommendedCard.cardBenefitList}" varStatus="status">
                        <c:set var="benefitName" value="${fn:replace(cardBenefit.cardBenefitDivision, '할인', '')}" />
                        <c:choose>
                          <c:when test="${status.index == 0}">
                            ${benefitName} ${cardBenefit.cardPercentage}%와 더불어
                          </c:when>
                          <c:when test="${status.index == 1}">
                            ${benefitName} ${cardBenefit.cardPercentage}%,
                          </c:when>
                          <c:when test="${status.last}">
                            ${benefitName} ${cardBenefit.cardPercentage}%
                          </c:when>
                          <c:otherwise>
                            ${benefitName} ${cardBenefit.cardPercentage}%,
                          </c:otherwise>
                        </c:choose>
                      </c:forEach>
                      할인 등 다양한 혜택을 누릴 수 있습니다!
                    </div>
                  </div>
                  <div class="reco-card-info--comment">회원님의 "슬기로운 소비 생활"을 위해 지금 당장 카드를 신청해 보세요!</div>
                </div>
                <div class="card-purchase text__center">
                  <div class="card--name">${recommendedCard.CARDNAME} 카드</div>
                  <div class="btn-container">
                    <button id="cardDetailBtn" class="btn btn__generate" data-card-no="${recommendedCard.CARDNO}">신청하러 가기</button>
                  </div>
                </div>
              </div>
            </c:if>
          </c:otherwise>
        </c:choose>

        <div class="hidden-ui"></div>
      </main>

    </div>
    <jsp:include page="/WEB-INF/views/jsp/components/Footer.jsp"/>
  </section>

  <input type="hidden" id="memberNo" value="${memberVo.memberNo}" />
  <input id="sellingCardNo" type="hidden" value="${sellingCard.SELLINGCARDNO}" />
</section>

<jsp:include page="/WEB-INF/views/jsp/components/scrollToTop.jsp" />

</body>
</html>

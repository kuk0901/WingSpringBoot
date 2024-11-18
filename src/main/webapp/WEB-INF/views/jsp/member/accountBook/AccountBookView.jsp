<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/views/jsp/common/common.jsp"/>
<!DOCTYPE html>
<html>
<%--member꺼--%>
<head>
  <meta charset="UTF-8">
  <title>가계부</title>
  <script defer type="module" src="/js/member/accountbook/memberAccountbook.js"></script>
  <link rel="stylesheet" href="/css/member/accountbook/memberAccountbook.css">
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
        <div class="title btn_red text__white">
          가계부
        </div>
      </div>
      <div class="month-switch one-line">
        <button id="prevMonth" class="month_btn">◀</button>
        <span id="currentMonth" class="month_box btn__generate">초기 화면</span>
        <button id="nextMonth" class="month_btn">▶</button>
      </div>
      <!--우선 위치대략적으로 정한거임-->
      <main  class="main-container">

        <form id="accountBookForm">
          <div class="entry-form one-line">
            <div class="entry-container one-line box__l">
              <div class="label-container">
                <label for="datepicker">날짜</label>
              </div>
              <div class="input-container">
                <input type="date" id="datepicker" />
              </div>
            </div>

            <div class="entry-container one-line box__s">
              <div class="label-container">
                <label for="incomeExpenseToggle">전환</label>
              </div>
              <div class="input-container">
                <select id="incomeExpenseToggle" class="box__xs">
                  <option value="expense">지출</option>
                  <option value="income">수입</option>
                </select>
              </div>
            </div>

            <div class="entry-container box__m">
              <div class="input-container">
                <select id="categorySelect">
                  <option value="">지출 카테고리 선택</option>
                  <option value="1">급여</option>
                  <option value="2">식비</option>
                </select>
              </div>
            </div>

            <div class="entry-container box__xl">
              <div class="input-container">
                <input type="text" id="contents" class="box__l" placeholder="요약:글자수제한 30자" maxlength="30" />
              </div>
            </div>

            <div class="entry-container box__s">
              <div class="input-container text__center">
                <select id="paymentMethodSelect">
                  <option value="">결제 수단 선택</option>
                  <option value="1">통장</option>
                  <option value="2">카드</option>
                </select>
              </div>
            </div>

            <div class="entry-container box__m">
              <div class="input-container text__center">
                <input type="number" id="amount" placeholder="결제 금액" min="100" max="9999999999999" />
              </div>
            </div>

            <div class="btn-container">
              <button id="addEntry" class="btn btn__generate btn__primary">추가</button>
            </div>
          </div>
        </form>

        <div class="summary-details one-line">
          <span class="total-entries" id="totalEntries">월간 내역 수: 0건</span>
          <div class="payback-container">
          <span class="payback-income" id="payBackIncome">페이백예정 금액: 0 원</span>
          </div>
          <div class="right-section">
            <span class="total-income" id="totalIncome">월 수입: 0원</span>
            <span class="total-expense" id="totalExpense">월 지출: 0원</span>
          </div>
        </div>
        <div class="entry-category info_head text__semibold one-line">
          <span class="category">카테고리</span>
          <span class="detail">내용</span>
          <span class="payment-method">결제수단</span>
          <span class="amount">결제금액</span>
        </div>
        <div class="entry-list">
          <div class="entry-date">
            <div class="entry-info one-line"></div>
          </div>
        </div>
        <div class="btn-container">
          <button id="loadMore" class="btn btn_plus">더보기</button>
        </div>
        <div class="hidden-ui"></div>
      </main>
    </div>

  </section>
  <jsp:include page="/WEB-INF/views/jsp/components/Footer.jsp"/>

  <input type="hidden" id="memberNo" value="${sessionScope.member.memberNo}" />
</section>
<jsp:include page="/WEB-INF/views/jsp/components/scrollToTop.jsp"/>
</body>

</html>
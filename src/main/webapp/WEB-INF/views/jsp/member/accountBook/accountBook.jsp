<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<%--member꺼--%>
<head>
    <meta charset="UTF-8">
    <title>가계부</title>

    <!-- jQuery 라이브러리 -->
    <script src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
            crossorigin="anonymous">
    </script>


    <script defer src="/js/member/accountbook/memberAccountbook.js"></script>
    <link rel="stylesheet" href="/css/member/accountbook/memberAccountbook.css">
    <jsp:include page="/WEB-INF/views/jsp/common/common.jsp"/>
</head>


<body>
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
                <span id="currentMonth" class="month_box btn__generate">2024년 10월</span>
                <button id="nextMonth" class="month_btn">▶</button>
            </div>
            <!--우선 위치대략적으로 정한거임-->
            <main  class="main-container">

                <form id="accountBookForm">
                <div class="entry-form">
                    <label for="datepicker">날짜:</label>
                    <input type="date" id="datepicker" />

                    <label for="incomeExpenseToggle">전환:</label>
                    <select id="incomeExpenseToggle">
                        <option value="expense">지출</option>
                        <option value="income">수입</option>
                    </select>

                    <select id="categorySelect">
                        <option value="">지출 카테고리 선택</option>
                        <option value="1">급여</option>
                        <option value="2">식비</option>
                    </select>

                    <input type="text" id="contents" placeholder="내용 입력" />

                    <select id="paymentMethodSelect">
                        <option value="">결제 수단 선택</option>
                        <option value="1">통장</option>
                        <option value="2">카드</option>
                    </select>

                    <input type="number" id="amount" placeholder="결제 금액" />
                    <button id="addEntry">추가</button>
                </div>
                </form>
                <div class="summary-details one-line">
                    <span class="total-entries" id="totalEntries">월간 내역 수: 0건</span>
                    <div class="right-section">
                        <span class="total-income" id="totalIncome">월수입: 0원</span>
                        <span class="total-expense" id="totalExpense">월지출: 0원</span>
                    </div>
                </div>
                <div class="entry-category info_head one-line">
                    <span class="category">카테고리</span>
                    <span class="detail">상세내용</span>
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
            </main>
        </div>
<%--        <jsp:include page="/WEB-INF/views/jsp/components/Footer.jsp"/>--%>
    </section>
    <input type="hidden" id="memberNo" value="${sessionScope.member.memberNo}" />
</section>
<jsp:include page="/WEB-INF/views/jsp/components/scrollToTop.jsp"/>
</body>

</html>
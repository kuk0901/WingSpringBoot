<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>PaymentListView</title>
  <link rel="stylesheet" href="/css/paymentMethod/adminPaymentMethod.css"></link>
  <script defer src="/js/paymentMethod/paymentMethod.js"></script>
</head>
<body>
<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp" />

  <div id="content" class="bg">

    <div class="title-container">
      <div class="title btn__yellow text__white">
        결제 수단 목록
      </div>
    </div>

    <div class="one-line-start">
      <div class="btn-container one-line-end">
        <a class="btn btn__generate" href="./add">
          결제 수단 추가
        </a>
      </div>
    </div>

    <main class="main-container payment-method__list bg__white">
      <div class="list-container list-container--title container-title">
        <div class="list--title list--div text__semibold box__s text__center">결제수단명</div>
        <div class="list--title list--div text__semibold box__l text__center">비고</div>
      </div>

      <c:choose>
        <c:when test="${not empty paymentMethodVoList}">
          <c:forEach items="${paymentMethodVoList}" var="paymentMethod" >
            <div class="list-container list-content bg__gray">
              <div class="list--div box__i text__center">${paymentMethod.paymentMethodName}</div>
              <div class="list--div box__o text__center">
<%--                <button class="btn btn__generate btn__blue btn_margin" onclick="moveModFunc(${paymentMethod.paymentMethodNo})">수정</button>--%>
                <button class="btn btn__generate btn__blue btn_margin" onclick="paymentMethodUpdate(${paymentMethod.paymentMethodNo});">수정</button>
                <button class="btn btn__generate btn__red" onclick="moveDelFunc(${paymentMethod.paymentMethodNo});">삭제</button>
              </div>
            </div>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <div class="list-container">
            <div class="list--div list__empty bg text__semibold text__correct">등록된 결제 수단이 없습니다.</div>
          </div>
        </c:otherwise>
      </c:choose>
    </main>
  </div>
</section>
</body>
</html>
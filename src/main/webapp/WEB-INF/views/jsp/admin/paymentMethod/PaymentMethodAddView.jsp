<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>PaymentMethodView</title>
  <%--    <jsp:include page="/WEB-INF/views/jsp/common/common.jsp" />--%>
  <link rel="stylesheet" href="/css/paymentMethod/adminPaymentMethod.css" />
  <script defer src="/js/paymentMethod/paymentMethod.js"></script>
</head>
<body>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp" />

  <div id="content" class="bg">

    <div class="title-container">
      <div id="titleName" class="title btn__yellow text__white">
        결제 수단 추가
      </div>
    </div>

    <main class="main-container payment-method__change">
      <form id="addPaymentMethodForm" class="addPaymentMethodForm">
        <div class="paymentMethod-form one-line">
          <div class="label-container bg__gray text__center text__semibold">
            <label for="paymentMethodName" class="info-item text__black">결제수단 명</label>
          </div>
          <div class="input-container bg__white text__black">
            <input type="text" id="paymentMethodName" name="paymentMethodName" class="info-item" placeholder="ex) 계좌이체" required>
          </div>
        </div>

        <div class="btn-container">
          <button id="paymentMethodAdd" type="submit" class="btn btn__generate btn--margin">등록</button>
          <button id="cancelAdd" type="button" class="btn btn__generate btn--margin">취소</button>
        </div>
      </form>
    </main>
  </div>
</section>


</body>
</html>
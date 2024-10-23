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
      <form id="addPaymentMethodForm">
        <div class="form-group one-line-start">
          <label for="paymentMethodName" class="info-item bg__gray text__black box__l text__center label-size">결제 수단명</label>
          <input type="text" id="paymentMethodName" name="paymentMethodName" class="info-item bg__white text__black box__l label-size" required>
        </div>

        <div class="btn-container">
          <button id="paymentMethodAdd" type="submit" class="btn btn__generate btn__blue btn--margin">등록</button>
          <button id="cancelAdd" type="button" class="btn btn__generate btn__red btn--margin">취소</button>
        </div>
      </form>
    </main>
  </div>
</section>


</body>
</html>
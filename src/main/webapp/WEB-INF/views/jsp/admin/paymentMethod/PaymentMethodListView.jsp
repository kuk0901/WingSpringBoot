<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>PaymentListView</title>
  <link rel="stylesheet" href="/css/admin/paymentMethod/adminPaymentMethod.css"></link>
  <script defer type="module" src="/js/admin/paymentMethod/paymentMethod.js"></script>
</head>
<body>
<jsp:include page="/WEB-INF/views/jsp/components/toast.jsp">
  <jsp:param value="${alertMsg}" name="alertMsg" />
</jsp:include>


<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp" />

  <div id="content" class="bg">

    <div class="title-container">
      <div class="title btn__yellow text__white">
        결제 수단 목록
      </div>
    </div>

    <div class="btn-container text__right">
      <a class="btn btn__generate btn__paymentMethod" href="./list/add">
        결제 수단 추가
      </a>
    </div>

    <main class="main-container payment-method__list bg__white">
      <div class="paymentMethod-container">
        <div class="list-container list-container--title container-title one-line">
          <div class="list--title list--div text__semibold box__s text__center">결제수단명</div>
          <div class="list--title text__semibold box__l text__center">비고</div>
        </div>

        <div class="list-container list-container--title container-title one-line">
          <div class="list--title list--div text__semibold box__s text__center">결제수단명</div>
          <div class="list--title text__semibold box__l text__center">비고</div>
        </div>

        <c:choose>
          <c:when test="${not empty paymentMethodVoList}">
            <c:forEach items="${paymentMethodVoList}" var="paymentMethod" >
              <div class="list-container list-content bg__gray one-line">
                <div id="paymentMethodName" class="list--div text__center">${paymentMethod.paymentMethodName}</div>
                <div class="list--note box__l text__center bg__white">
                  <button class="btn btn__generate btn__blue update-pm-btn" data-pmn="${paymentMethod.paymentMethodNo}">수정</button>
                  <button class="btn btn__generate btn__red remove-pm-btn" data-pmn="${paymentMethod.paymentMethodNo}">삭제</button>
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
      </div>
    </main>
  </div>
</section>

<jsp:include page="/WEB-INF/views/jsp/components/scrollToTop.jsp" />
</body>
</html>
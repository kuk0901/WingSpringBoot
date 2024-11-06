<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Dashboard</title>
  <link rel="stylesheet" href="/css/admin/dashboard/adminDashboard.css"></link>
  <script defer type="module" src="/js/admin/dashboard/dashboardChartFncs.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script></head>
<body>

<jsp:include page="/WEB-INF/views/jsp/components/toast.jsp">
  <jsp:param value="${alertMsg}" name="alertMsg" />
</jsp:include>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp" />

  <div id="content" class="bg">
    <section class="totalCardsSold-container">
      <div class="title-container">
        <div class="title btn__yellow text__white">
          판매 카드 수(년)
        </div>
      </div>

      <main class="main-container">
        <canvas id="totalCards" height="600" data-total-card-list='${totalCardsData}'></canvas>
      </main>
    </section>

    <section class="recommendedCardsPurchased-container">
      <div class="title-container">
        <div class="title btn__yellow text__white">
          추천 카드 구매 수(년)
        </div>
      </div>

      <main class="main-container">
        <canvas id="recommendedCards" height="600" data-reco-card-list='${recommendedCardsData}'></canvas>
      </main>
    </section>

    <section class="terminatedCards-container">
      <div class="title-container">
        <div class="title btn__yellow text__white">
          해지 카드 수(년)
        </div>
      </div>

      <main class="main-container">
        <canvas id="terminatedCards" height="600" data-term-card-list='${terminatedCardsData}'></canvas>
      </main>
    </section>
  </div>

</section>

<jsp:include page="/WEB-INF/views/jsp/components/scrollToTop.jsp"/>
</body>
</html>
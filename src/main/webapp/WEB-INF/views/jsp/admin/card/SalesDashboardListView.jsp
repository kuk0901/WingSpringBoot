<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>salesDashboardList</title>
  <link rel="stylesheet" href="/css/card/adminCard.css"></link>
  <script defer src="/js/sellingCard/AjaxSellingCardDetail.js"></script>
</head>
<body>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp" />

  <div id="content">
    <div class="title-container">
      <div class="title btn__yellow text__white">
        판매 카드 현황
      </div>
    </div>

    <div id="cardNoFormCon" class="cardNoForm-container">
      <form id="cardNoForm" action="./list" method="post">
        <label for="cardNumber" class="selectCard bg__gray text__black text__center">카드 종류</label>
        <select id="cardNumber" name="cardNo" class="cardNo">
          <option value="0" ${cardNo == '0' ? "selected" : ""} >전체</option>
          <option value="1" ${cardNo == '1' ? "selected" : ""}>Traffic</option>
          <option value="2" ${cardNo == '2' ? "selected" : ""}>Shopping</option>
          <option value="3" ${cardNo == '3' ? "selected" : ""}>Daily</option>
        </select>
        <input type="submit" value="검색" class="btn btn__generate"/>
      </form>
    </div>

    <main class="main-container bg__white">
      <div class="list-container list-container--title">
        <div class="list--title list--div text__semibold box__s">번호</div>
        <div class="list--title list--div text__semibold box__l">판매 날짜</div>
        <div class="list--title list--div text__semibold box__s">판매 분류</div>
        <div class="list--title list--div text__semibold box__l">구매자 이메일</div>
        <div class="list--title list--div text__semibold box__sm">카드 해지 여부</div>
      </div>

      <c:choose>
        <c:when test="${not empty sellingCardVoList}">
          <c:forEach items="${sellingCardVoList}" var="sellingCard" >
            <div class="list-container list-content" data-selling-card-no="${sellingCard.sellingCardNo}">
              <div class="list--div box__s">${sellingCard.sellingCardNo}</div>
              <div class="list--div box__l">
                <fmt:formatDate value="${sellingCard.sellingDate}" pattern="yyyy-MM-dd HH:mm:ss" />
              </div>
              <div class="list--div box__s">${sellingCard.cardRecommend}</div>
              <div class="list--div box__l">${sellingCard.email}</div>
              <div class="list--div box__sm">${sellingCard.cardTermination}</div>
            </div>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <div class="list-container">
            <div class="list--div list__empty bg text__semibold text__correct">판매된 카드가 없습니다.</div>
          </div>
        </c:otherwise>
      </c:choose>
    </main>

    <jsp:include page="/WEB-INF/views/jsp/common/Paging.jsp">
      <jsp:param value="${pagingMap}" name="pagingMap" />
    </jsp:include>

    <form id="pagingForm" action="./list" method="post">
      <input type="hidden" id="curPage" name="curPage" value="${pagingMap.pagingVo.curPage}" />
      <input type="hidden" id="cardNo" name="cardNo" value="${cardNo}"/>
    </form>
  </div>

</section>

</body>
</html>
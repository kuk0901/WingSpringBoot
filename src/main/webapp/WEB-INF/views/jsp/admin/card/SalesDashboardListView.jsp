<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>salesDashboardList</title>
  <link rel="stylesheet" href="/css/admin/card/adminSalesDashboard.css"></link>
  <script defer type="module" src="/js/admin/card/AjaxSellingCardDetail.js"></script>
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
        판매 카드 현황
      </div>
    </div>

    <div class="search-container">
      <form id="searchForm" class="one-line" action="./list" method="post">
        <div class="cardNoForm-container">
          <label for="cardNumber" class="selectCard bg__gray text__black text__center">카드 종류</label>
          <select id="cardNumber" name="cardNo" class="cardNo">
            <option value="0" ${cardNo == '0' ? "selected" : ""}>전체</option>
            <option value="1" ${cardNo == '1' ? "selected" : ""}>Traffic</option>
            <option value="2" ${cardNo == '2' ? "selected" : ""}>Shopping</option>
            <option value="3" ${cardNo == '3' ? "selected" : ""}>Daily</option>
          </select>
        </div>

        <div class="terSeasonForm-container">
          <label for="termination" class="selectReason bg__gray text__black text__center">해지 여부</label>
          <select id="termination" name="termination" class="reasonSelect">
            <option value="all" ${termination == "all" ? "selected" : ""}>전체</option>
            <option value="Y" ${termination == "Y" ? "selected" : ""}>카드 해지 O</option>
            <option value="N" ${termination == "N" ? "selected" : ""}>카드 해지 X</option>
          </select>
        </div>

        <input type="hidden" name="curPage" value="1">
        <input id="searchFormBtn" type="submit" value="검색" class="btn btn__generate"/>
      </form>
    </div>


    <main class="main-container">
      <div class="list-container list-container--title bg__white">
        <div class="list--title list--div text__semibold box__s">번호</div>
        <div class="list--title list--div text__semibold box__l">판매 날짜</div>
        <div class="list--title list--div text__semibold box__s">판매 분류</div>
        <div class="list--title list--div text__semibold box__l">구매자 이메일</div>
        <div class="list--title list--div text__semibold box__sm">카드 해지 여부</div>
      </div>

      <c:choose>
        <c:when test="${not empty sellingCardVoList}">
          <div class="bg__white">
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
            </div>
        </c:when>
        <c:otherwise>
          <div class="list-container">
            <div class="list--div list__empty bg text__semibold text__correct">판매된 카드가 없습니다.</div>
          </div>
        </c:otherwise>
      </c:choose>
    </main>

    <c:if test="${not empty sellingCardVoList}">
      <jsp:include page="/WEB-INF/views/jsp/common/Paging.jsp">
        <jsp:param value="${pagingMap}" name="pagingMap" />
      </jsp:include>
    </c:if>

    <form id="pagingForm" action="./list" method="post">
      <input type="hidden" id="curPage" name="curPage" value="${pagingMap.pagingVo.curPage}" />
      <input type="hidden" id="cardNo" name="cardNo" value="${cardNo}"/>
      <input type="hidden" id="cardTermination" name="termination" value="${termination}"/>
    </form>
  </div>

</section>

</body>
</html>
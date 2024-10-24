<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>paymentManagementList</title>
  <link rel="stylesheet" href="/css/card/adminCard.css"></link>
</head>
<body>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp" />

  <div id="content">
    <div class="title-container">
      <div class="title btn__yellow text__white">
        카드 관리
      </div>
    </div>

    <div class="one-line">
      <div id="categoryFormCon" class="categoryForm-container">
        <form id="categoryForm" action="./list" method="post">
          <label for="categoryName" class="selectCard bg__gray text__black text__center">분류</label>
          <select id="categoryName" name="categoryName" class="categoryName">
            <option value="all" ${categoryName == 'all' ? "selected" : ""} >전체</option>
            <option value="Traffic" ${categoryName == 'Traffic' ? "selected" : ""}>Traffic</option>
            <option value="Shopping" ${categoryName == 'Shopping' ? "selected" : ""}>Shopping</option>
            <option value="Daily" ${categoryName == 'Daily' ? "selected" : ""}>Daily</option>
          </select>
          <input type="submit" value="검색" class="btn btn__generate"/>
        </form>
      </div>

      <div class="btn-container">
        <button class="btn btn__generate">카드 추가</button>
      </div>
    </div>


    <main class="main-container bg__white card-list-container">
      <div class="list-container list-container--title bg__white">
        <div class="list--title list--div text__semibold box__l">카드명</div>
        <div class="list--title list--div text__semibold box__l">혜택 요약</div>
        <div class="list--title list--div text__semibold box__s">등록 날짜</div>
        <div class="list--title list--div text__semibold box__s">분류</div>
        <div class="list--title list--div text__semibold box__sm">비고</div>
      </div>

      <c:choose>
        <c:when test="${not empty cardList}">
          <c:forEach items="${cardList}" var="card" >
            <div class="list-container list-content card-content h__m" data-selling-card-no="${card.cardNo}">
              <div class="list--div box__l">
                <div class="text__center text__semibold card--name">${card.cardName}</div>
                <div class="img-container">
                  <img alt="${card.cardName}이미지" class="card--img"/>
                </div>
              </div>
              <div class="list--div box__l">${card.summationBenefit}</div>
              <div class="list--div box__s">
                <fmt:formatDate value="${card.registerDate}" pattern="yyyy-MM-dd" />
              </div>
              <div class="list--div box__s">${card.categoryName}</div>
              <div class="list--div box__sm">
                <div class="btn-container">
                  <button class="btn btn__generate">삭제</button>
                  <button class="btn btn__generate">상세보기</button>
                </div>
              </div>
            </div>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <div class="list-container">
            <div class="list--div list__empty bg text__semibold text__correct">등록된 카드가 없습니다.</div>
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
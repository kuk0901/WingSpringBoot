<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>paymentManagementList</title>
  <link rel="stylesheet" href="/css/admin/card/adminProductManagement.css" />
  <script defer type="module" src="/js/admin/card/AjaxCardDetail.js"></script>
  <script defer type="module" src="/js/admin/card/AjaxCardDelete.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/views/jsp/components/toast.jsp">
  <jsp:param value="${alertMsg}" name="alertMsg" />
</jsp:include>

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

      <div class="card--count one-line">
        <div class="label-container bg__gray text__black text__center text__semibold">
          <label for="totalCount" class="count--label">등록된 카드 수</label>
        </div>
        <div class="input-container">
          <input id="totalCount" class="count--input" value="${pagingMap.totalCount}" readonly/>
        </div>
      </div>

      <div class="btn-container">
        <a href="/admin/productManagement/list/card/insert?curPage=${pagingMap.pagingVo.curPage}&categoryName=${categoryName}" class="btn btn__generate btn__p">카드 추가</a>
      </div>
    </div>

    <main class="main-container bg__white card-list-container">
      <div class="list-container list-container--title bg__white">
        <div class="list--title list--div text__semibold box__l text__center">카드명</div>
        <div class="list--title list--div text__semibold box__m text__center">혜택 요약</div>
        <div class="list--title list--div text__semibold box__m text__center">등록 날짜</div>
        <div class="list--title list--div text__semibold box__s text__center">분류</div>
        <div class="list--title list--div text__semibold box__sm text__center">비고</div>
      </div>

      <c:choose>
        <c:when test="${not empty cardList}">
          <c:forEach items="${cardList}" var="card" >
            <div class="list-container list-content card-content h__m">
              <div class="list--simple box__l">
                <div class="text__center text__semibold card--name">${card.cardName}</div>
                <div class="img-container">
                  <img src="/img/card/${card.storedFileName}" alt="${card.cardName}이미지" class="card--img"/>
                </div>
              </div>

              <div class="list--simple box__m card-benefit">
                <ul class="ul--ui">
                <c:forEach items="${benefitMap[card.cardNo]}" var="benefit" varStatus="status">
                  <c:if test="${status.index < 3}">
                    <li class="li--ui"><span class="list--style"></span>${benefit.cardBenefitDetail} ${benefit.cardPercentage}% ${benefit.cardBenefitDivision} 할인</li>
                  </c:if>
                </c:forEach>
                <c:if test="${fn:length(benefitMap[card.cardNo]) > 3}">
                  <span>(외 ${fn:length(benefitMap[card.cardNo]) - 3}개)</span>
                </c:if>
                </ul>
              </div>

              <div class="list--simple box__m">
                <c:set var="today" value="<%=new java.util.Date()%>" />
                <fmt:formatDate var="nowDate" value="${today}" pattern="yyyyMMdd" />
                <fmt:formatDate var="cardDate" value="${card.registerDate}" pattern="yyyyMMdd" />
                <fmt:formatDate value="${card.registerDate}" pattern="yyyy-MM-dd" var="formattedDate" />

                <c:choose>
                  <c:when test="${nowDate < cardDate}">
                    <div class="date__warning">
                      <span class="text__error text-date__error">등록 날짜에 판매될 예정입니다.</span><br>
                      <span>${formattedDate}</span>
                    </div>
                  </c:when>
                  <c:otherwise>
                    <div class="date__success">
                      <span>${formattedDate}</span>
                    </div>
                  </c:otherwise>
                </c:choose>
              </div>

              <div class="list--simple box__s">${card.categoryName}</div>

              <div class="list--simple box__sm">
                <div class="btn-container">
                  <button class="btn btn__generate remove-card-btn btn__p" data-card-no="${card.cardNo}">삭제</button>
                  <button class="btn btn__generate detail-card-btn btn__p" data-card-no="${card.cardNo}">상세보기</button>
                </div>
              </div>
            </div>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <div class="list-container">
            <div class="list--simple list__empty bg text__semibold text__correct">등록된 카드가 없습니다.</div>
          </div>
        </c:otherwise>
      </c:choose>
    </main>

    <c:if test="${not empty cardList}">
      <jsp:include page="/WEB-INF/views/jsp/common/Paging.jsp">
        <jsp:param value="${pagingMap}" name="pagingMap" />
      </jsp:include>
    </c:if>

    <form id="pagingForm" action="./list" method="post">
      <input type="hidden" id="curPage" name="curPage" value="${pagingMap.pagingVo.curPage}" />
      <input type="hidden" id="cardNo" name="cardNo" value="${cardNo}"/>
      <input type="hidden" name="categoryName" value="${categoryName}"/>
    </form>
  </div>

</section>

<jsp:include page="/WEB-INF/views/jsp/components/scrollToTop.jsp" />
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>CategoryListView</title>
  <link rel="stylesheet" href="/css/member/inquiry/memberInquiry.css" />
  <script defer type="module" src="/js/member/inquiry/inquiry.js"></script>
</head>
<body>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavCs.jsp" />

  <div id="content" class="bg">

    <div class="title-container">
      <div class="title btn__yellow text__white">
        1대1 문의사항
      </div>
    </div>

    <div class="search-container one-line">
      <div class="btn-container">
        <a id="addInquiryView" class="btn btn__generate btn__inquiry" href="./list/add">
          문의하기
        </a>
      </div>
    </div>

    <main class="main-container inquiry__list">
      <div class="inquiry-container">
        <div class="list-container list-container--title container-title one-line bg__white">
          <div class="list--supply text__semibold text__center">번호</div>
          <div class="list--title text__semibold text__center">제목</div>
          <div class="list--date text__semibold text__center">작성날짜</div>
          <div class="list--supply text__semibold text__center">분류</div>
          <div class="list--supply text__semibold text__center">관리자답변</div>
        </div>

        <c:choose>
          <c:when test="${not empty inquiryList}">
            <div class="bg__white">
              <c:forEach items="${inquiryList}" var="inquiryVo" >
                <div class="list-container list-content one-line" data-inquiry-no="${inquiryVo.inquiryNo}">
                  <div class="list--supply text__center">${inquiryVo.inquiryNo}</div>
                  <div class="list--title text__center">${inquiryVo.title}</div>
                  <div class="list--date text__center">
                    <fmt:formatDate value="${inquiryVo.creDate}" pattern="yyyy-MM-dd HH:mm:ss" />
                  </div>
                  <div class="list--supply text__center">${inquiryVo.division}</div>
                  <div class="list--supply text__center">${inquiryVo.answerTermination}</div>
                </div>
              </c:forEach>
            </div>
          </c:when>
          <c:otherwise>
            <div class="list-container">
              <div class="list--div list__empty bg text__semibold text__correct text__center">등록된 1대1 문의사항이 없습니다.</div>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </main>

    <jsp:include page="/WEB-INF/views/jsp/common/Paging.jsp">
      <jsp:param value="${pagingMap}" name="pagingMap" />
    </jsp:include>

    <form id="pagingForm" action="./list" method="post">
      <input type="hidden" id="curPage" name="curPage" value="${pagingMap.pagingVo.curPage}" />
    </form>

  </div>

  <input id="abc" type="hidden" value="${sessionScope.member.memberNo}">
</section>

</body>

</html>
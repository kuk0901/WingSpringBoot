<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>MinusCategoryView</title>
  <link rel="stylesheet" href="/css/category/adminCategory.css"></link>
  <script defer src="/js/category/category.js"></script>
</head>
<body>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp" />

  <div id="content" class="bg">

    <div class="title-container">
      <div class="title btn__yellow text__white">
        카테고리 목록
      </div>
    </div>

    <div class="one-line">
      <div class="btn-container">
        <button class="btn btn__generate" onclick="moveAddFunc()">
          카테고리 추가
        </button>
      </div>
    </div>

    <main class="main-container bg__white">
      <div class="list-container list-container--title">
        <div class="list--title list--div text__semibold box__s">카테고리명</div>
        <div class="list--title list--div text__semibold box__l">+/- 설정</div>
        <div class="list--title list--div text__semibold box__s">비고</div>
      </div>

      <c:choose>
        <c:when test="${not empty 'minusCategoryList' or not empty 'plusCategoryList'}">
          <c:forEach var="categoryVo" items="${minusCategoryList}">
            <c:if test="${categoryVo.categoryNo != 1}">
              <div class="list-container list-content bg__gray" data-category-no="${categoryVo.categoryNo}" data-category-type="minus">
                <div class="list--div box__s">${categoryVo.categoryName != '' ? categoryVo.categoryName : '없음'}</div>
                <div class="list--div box__s">지출</div>
                <div class="list--div box__l">
                  <button class="btn btn__generate btn__blue" onclick="moveModFunc('minus', ${categoryVo.categoryNo})">수정</button>
                  <button class="btn btn__generate btn__red" onclick="moveDelFunc('minus', ${categoryVo.categoryNo})">삭제</button>
                </div>
              </div>
            </c:if>
          </c:forEach>
          <c:forEach var="categoryVo" items="${plusCategoryList}">
            <c:if test="${categoryVo.categoryNo != 1}">
              <div class="list-container list-content bg__gray" data-category-no="${categoryVo.categoryNo}" data-category-type="plus">
                <div class="list--div box__s">${categoryVo.categoryName != '' ? categoryVo.categoryName : '없음'}</div>
                <div class="list--div box__s">수입</div>
                <div class="list--div box__l">
                  <button class="btn btn__generate btn__blue" onclick="moveModFunc('plus', ${categoryVo.categoryNo})">수정</button>
                  <button class="btn btn__generate btn__red" onclick="moveDelFunc('plus', ${categoryVo.categoryNo})">삭제</button>
                </div>
              </div>
            </c:if>
          </c:forEach>
        </c:when>
        <c:otherwise>
          <div class="list-container">
            <div class="list--div list__empty bg text__semibold text__correct">카테고리가 존재하지 않습니다</div>
          </div>
        </c:otherwise>
      </c:choose>


<%--        <table>--%>

<%--          <tr>--%>
<%--            <th colspan="2">카테고리명</th>--%>
<%--            <th colspan="2">+/- 설정</th>--%>
<%--            <th>비고</th>--%>
<%--          </tr>--%>



<%--          </c:choose>--%>
<%--        </table>--%>
    </main>
  </div>
</section>

</body>
</html>
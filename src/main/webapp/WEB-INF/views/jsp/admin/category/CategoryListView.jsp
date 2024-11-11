<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>CategoryListView</title>
  <link rel="stylesheet" href="/css/admin/category/adminCategory.css"></link>
  <script defer type="module" src="/js/admin/category/category.js"></script>
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

    <div class="btn-container text__right">
      <a class="btn btn__generate btn__category" href="./add">
        카테고리 추가
      </a>
    </div>

    <main class="main-container bg__white one-line">
      <div class="category-container">
        <div class="sub-title-container">
          <div class="sub-title text__center text__semibold">마이너스 카테고리</div>
        </div>

        <div class="list-container list-container--title">
          <div class="list--title list--div text__center text__semibold box__s">카테고리명</div>
          <div class="list--title list--div text__center text__semibold box__l">+/- 설정</div>
          <div class="list--title list--div text__center text__semibold box__s">비고</div>
        </div>

        <c:choose>
          <c:when test="${not empty 'minusCategoryList'}">
            <c:forEach var="categoryVo" items="${minusCategoryList}">
              <div class="list-container list-content bg__gray" data-category-no="${categoryVo.categoryNo}">
                <div class="list--div text__center box__s">${categoryVo.categoryName != '' ? categoryVo.categoryName : '없음'}</div>
                <div id="minusValue" class="list--div text__center box__s">지출</div>
                <div class="list--div box__l text__center bg__white">
                  <button class="btn btn__generate moveModFunc btn__blue" data-category-no="${categoryVo.categoryNo}" data-category-type="minus">수정</button>
                  <button class="btn btn__generate moveDelFunc btn__red" data-category-no="${categoryVo.categoryNo}" data-category-type="minus">삭제</button>
                </div>
              </div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <div class="list-container">
              <div class="list--div list__empty bg text__semibold text__correct">카테고리가 존재하지 않습니다</div>
            </div>
          </c:otherwise>
        </c:choose>
      </div>

      <div class="category-container">
        <div class="sub-title-container">
          <div class="sub-title text__center text__semibold">플러스 카테고리</div>
        </div>

        <div class="list-container list-container--title">
          <div class="list--title list--div text__center text__semibold box__s">카테고리명</div>
          <div class="list--title list--div text__center text__semibold box__l">+/- 설정</div>
          <div class="list--title list--div text__center text__semibold box__s">비고</div>
        </div>

        <c:choose>
          <c:when test="${not empty 'plusCategoryList'}">
            <c:forEach var="categoryVo" items="${plusCategoryList}">
                <div class="list-container list-content bg__gray" data-category-no="${categoryVo.categoryNo}">
                  <div class="list--div text__center box__s">${categoryVo.categoryName != '' ? categoryVo.categoryName : '없음'}</div>
                  <div id="plusValue" class="list--div text__center box__s">수입</div>
                  <div class="list--div box__l text__center bg__white">
                    <button class="btn btn__generate moveModFunc btn__blue" data-category-no="${categoryVo.categoryNo}" data-category-type="plus">수정</button>
                    <button class="btn btn__generate moveDelFunc btn__red" data-category-no="${categoryVo.categoryNo}" data-category-type="plus">삭제</button>
                  </div>
                </div>
            </c:forEach>
          </c:when>
          <c:otherwise>
            <div class="list-container">
              <div class="list--div list__empty bg text__semibold text__correct">카테고리가 존재하지 않습니다</div>
            </div>
          </c:otherwise>
        </c:choose>
      </div>
    </main>
  </div>
</section>

</body>
</html>
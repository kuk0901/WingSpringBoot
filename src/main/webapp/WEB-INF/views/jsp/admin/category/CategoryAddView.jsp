<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>CategoryListView</title>
<%--  <jsp:include page="/WEB-INF/views/jsp/common/common.jsp" />--%>
  <link rel="stylesheet" href="/css/admin/category/adminCategory.css" />
  <script defer type="module" src="/js/admin/category/category.js"></script>
</head>
<body>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp" />

  <div id="content" class="bg">
    <div class="title-container">
      <div id="titleName" class="title btn__yellow text__white">
        카테고리 추가
      </div>
    </div>

    <main class="main-container">
      <form id="categoryForm" class="categoryForm">

        <div class="style-category">
          <div class="category-container one-line">
            <div class="label-container bg__gray text__center">
              <label for="categoryName" class="text__semibold">카테고리 명</label>
            </div>
            <div class="input-container bg__white">
              <input id="categoryName" type='text' name='categoryName' class="categoryName" placeholder="ex) 여행"><br>
            </div>

            <div class="existing-categories bg">
              <div class="bg__gray text__center text__semibold">기존 카테고리</div>
              <div id="categoryList" class="category-container bg__white">
                <div id="categoryItems" class="text__center categoryItems">
                  <c:forEach items="${minusCategories}" var="category" varStatus="status">
                    <span class="category-item minus">${category}<c:if test="${!status.last}">, </c:if></span>
                  </c:forEach>
                  <c:forEach items="${plusCategories}" var="category" varStatus="status">
                    <span class="category-item plus" style="display: none;">${category}<c:if test="${!status.last}">, </c:if></span>
                  </c:forEach>
                </div>
              </div>
            </div>
          </div>

          <div class="category-container one-line">
            <div class="label-container bg__gray text__center">
              <label for="division" class="text__semibold">수입 / 지출</label>
            </div>
            <div class="input-container select-container bg__gray">
              <select id="division" name="division" class="select bg__gray">
                <option value="minus" selected>-</option>
                <option value="plus">+</option>
              </select>
            </div>
          </div>
        </div>

        <div class="btn-container">
          <button id="categoryAdd" class="btn btn__generate btn--margin">등록</button>
          <button id="cancelAdd" class="btn btn__generate btn--margin">취소</button>
        </div>


      </form>
  </main>
  </div>
</section>


</body>
<script>

</script>
</html>
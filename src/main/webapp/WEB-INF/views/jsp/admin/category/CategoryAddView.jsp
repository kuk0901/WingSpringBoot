<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>CategoryListView</title>
<%--  <jsp:include page="/WEB-INF/views/jsp/common/common.jsp" />--%>
  <link rel="stylesheet" href="/css/category/adminCategory.css" />
  <script defer type="module" src="/js/category/category.js"></script>
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

        <div class="category-container one-line">
          <div class="label-container">
            <label for="categoryName">카테고리 명</label>
          </div>
          <div class="input-container">
            <input id="categoryName" type='text' name='categoryName'><br>
          </div>

          <div class="category-container one-line">
            <div class="label-container">
              <label for="division">수입 / 지출</label>
            </div>
            <div class="input-container">
              <select id="division" name="division">
                <option value="minus" selected>-</option>
                <option value="plus">+</option>
              </select>
            </div>
          </div>
        </div>

        <div class="btn-container">
          <button id="paymentMethodAdd" type="submit" class="btn btn__generate btn__blue btn--margin">등록</button>
          <button id="cancelAdd" type="button" class="btn btn__generate btn__red btn--margin">취소</button>
        </div>


      </form>
  </main>
  </div>
</section>


</body>
<script>

</script>
</html>
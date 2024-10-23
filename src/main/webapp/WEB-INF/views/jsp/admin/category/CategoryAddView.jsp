<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>MinusCategoryView</title>
      <jsp:include page="/WEB-INF/views/jsp/common/common.jsp" />
  <link rel="stylesheet" href="/css/auth/signin.css" />
      <script defer src="/js/auth/signin.js"></script>
</head>
<body>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp" />

  <div id="content">
    <div>
      카테고리 추가
    </div>
    <form id="categoryForm" class="categoryForm one-line">
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

    </form>
  </div>
</section>


</body>
<script>

</script>
</html>
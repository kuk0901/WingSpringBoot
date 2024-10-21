<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>

<head>
  <meta charset="UTF-8">
  <title>가계부</title>
  <script src="https://code.jquery.com/jquery-3.7.0.js"
          integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM="
          crossorigin="anonymous">
  </script>
  <script defer src="/js/accountbook/accountbook.js"></script>
  <script type="text/javascript">

  </script>
  <link rel="stylesheet" href="/css/accountbook/accountbook.css">
  <jsp:include page="/WEB-INF/views/jsp/common/common.jsp"/>
</head>


<body>
<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp"/>
  <div id="content">
    <div class="title-container">
      <div class="title btn__yellow text__white">
       가계부 현황
      </div>
    </div>
    <div class="top-container">
      <div class="category-container">Top카테고리</div>
      <div class="payment-container">Top결제수단</div>
    </div>

    <div class="form-container">
      <form id="searchForm">
        <div class="categoryForm-container">
          <label for="categorySelect">카테고리 선택:</label>
          <select name="category" id="categorySelect">
            <option value="all" selected>전체 선택</option> <!-- 전체 선택 옵션을 기본으로 선택 -->

            <c:forEach var="category" items="${categoryList}">
              <c:if test="${not empty category}">
                <option value="${category}">${category}</option>
              </c:if>
            </c:forEach>
          </select>
        </div>

        <div class="paymentForm-container">
          <label for="paymentMethodSelect">결제 방법 선택:</label>
          <select name="paymentMethod" id="paymentMethodSelect">
            <option value="all" selected>전체 선택</option> <!-- 전체 선택 옵션을 기본으로 선택 -->
            <c:forEach var="paymentMethod" items="${paymentMethodList}">
              <option value="${paymentMethod}">${paymentMethod}</option>
            </c:forEach>
          </select>
        </div>
        <input type="submit" value="검색">
      </form>
    </div>

    <main  class="main-container">
      <div class="table_container">
        <table>
          <tr>
            <th>번호</th>
            <th>작성일자</th>
            <th>이메일</th>
            <th>카테고리</th>
            <th>결제 수단</th>
            <th>금액</th>
          </tr>
          <tbody id="resultContainer">
            <c:forEach var="accountBook" items="${accountBookList}">
              <tr>
                <td>${accountBook.accountBookNo}</td>
                <td>${accountBook.creDate}</td>
                <td>${accountBook.memberEmail}</td>
                <td>${accountBook.minusCategoryName}</td>
                <td>${accountBook.paymentMethodName}</td>
                <td>${accountBook.paymentAmount}</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </main>
  </div>
</section>
</body>
</html>
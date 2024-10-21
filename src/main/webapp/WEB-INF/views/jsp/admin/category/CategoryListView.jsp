<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>MinusCategoryView</title>
  <%--    <jsp:include page="/WEB-INF/views/jsp/common/common.jsp" />--%>
  <link rel="stylesheet" href="/css/auth/signin.css" />
  <%--    <script defer src="/js/auth/signin.js"></script>--%>
</head>
<body>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavAdmin.jsp" />

  <div id="content">

    <div class="title-container">
      <div>

      </div>
    </div>

    <div class="btn-container">
      <button class="btn btn__generate" onclick="moveAddFunc()">
        카테고리 추가
      </button>
    </div>

    <main class="main-container bg__white">
      <div>
        <table>

          <tr>
            <th colspan="2">카테고리명</th>
            <th>비고</th>
          </tr>

          <c:choose>

            <c:when test="${not empty minusCategoryList}">

              <c:forEach var="minusCategoryVo" items="${minusCategoryList}">
                <c:if test="${minusCategoryVo.categoryNo != 1}">
                  <tr>
                    <td colspan="2">
                        ${minusCategoryVo.categoryName != '' ? minusCategoryVo.categoryName : '없음'}
                    </td>
                    <td>
                      <button class="btn btn__blue"
                              onclick="moveModFunc(${minusCategoryVo.categoryNo})">수정</button>
                      <button class="btn bg__red text__white"
                              onclick="moveDelFunc(${minusCategoryVo.categoryNo})">삭제</button>
                    </td>
                  </tr>
                </c:if>
              </c:forEach>
            </c:when>

            <c:otherwise>
              <tr>
                <td colspan="2">카테고리가 존재하지 않습니다</td>
              </tr>
            </c:otherwise>

          </c:choose>
        </table>
      </div>
    </main>
  </div>
</section>


</body>
<script>

</script>
</html>
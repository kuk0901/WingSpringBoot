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
    <div>
      카테고리 추가
    </div>
    <form action='add' method='post'>
      이름: <input type='text' name='categoryName'><br>


      <input type='submit' value='추가'>
      <input type='reset' value='취소'>
    </form>
  </div>
</section>


</body>
<script>

</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>500</title>
  <link rel="stylesheet" href="/css/common/error.css">
  <script defer src="/js/error/error.js"></script>
</head>
<body>

<jsp:include page="/WEB-INF/views/jsp/components/Header.jsp" />

<jsp:include page="/WEB-INF/views/jsp/components/toast.jsp">
  <jsp:param value="${alertMsg}" name="alertMsg" />
</jsp:include>

<div class="error-container">
  <h1 class="status-code text__bold">500 - 서버 오류가 발생했습니다.</h1>
  <p class="error-msg text__semibold">서버에서 문제가 발생했습니다. <br /> 잠시 후 다시 시도해주세요.</p>
  <button id="goBackPage" class="btn btn__generate btn__primary">이전 페이지로 돌아가기</button>
</div>

</body>
</html>
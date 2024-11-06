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

    <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        1대1 문의사항 추가
      </div>
    </div>

    <main class="main-container bg__white">
      <div class="inquiry-container">
        <div class="inquiry-title one-line">
          <input type="hidden" id="memberNo"  value="${member.memberNo}">

          <div class="info-title bg__gray text__black box__l text__center">제목</div>
          <input id="titleVal" type="text" class="info-item bg__white text__black box__l" placeholder="제목을 입력하세요">

          <div class="info-title bg__gray text__black box__l text__center">분류</div>
          <input id="divisionVal" type="text" class="info-dv-item bg__white text__black box__l" placeholder="분류를 입력하세요">
        </div>

        <div class="inquiry-sub one-line">
          <div class="info-title bg__gray text__black box__l text__center">작성자</div>
          <input id="emailVal" type="text" class="info-writer bg__white text__black box__l" value="${member.email}" readonly>

          <div class="info-title bg__gray text__black box__l text__center">작성일</div>
          <input type="text" id="writeDate" class="info-date bg__white text__black box__l" value="${currentDate}" readonly>
        </div>

        <div class="info-content-div reason--title bg__gray text__black box__xl text__center">문의 내용</div>

        <div class="info-content bg__white text__black box__l">
          <textarea id="contentVal" class="contentArea" placeholder="문의 내용을 입력하세요"></textarea>
        </div>
      </div>
    </main>

    <div class="btn-container-all one-line">
      <div class="btn-container">
        <button id="listMove" class="btn btn__generate listMove text__center" data-cur-page="${data.curPage}">
          돌아가기
        </button>
      </div>
      <div class="btn-container">
        <button id="addInquiry" class="btn btn__generate listMove text__center">
          문의 등록
        </button>
      </div>
    </div>

  </div>
</section>

</body>
<script>

</script>
</html>
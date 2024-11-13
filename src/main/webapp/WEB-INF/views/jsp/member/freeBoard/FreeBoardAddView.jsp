<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/jsp/common/common.jsp" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>FreeBoardAdd</title>
  <link rel="stylesheet" href="/css/member/freeBoard/memberFreeBoard.css" />
  <script defer type="module" src="/js/member/freeBoard/freeBoard.js"></script>
</head>
<body>

<section id="root">
  <jsp:include page="/WEB-INF/views/jsp/components/NavMember.jsp" />

  <div id="content" class="bg">

    <div class="title-container one-line">
      <div id="title" class="title btn__yellow text__white">
        자유게시판 게시글 추가
      </div>
    </div>

    <main class="main-container bg__white">
      <div class="freeBoard-container">
        <div class="freeBoard-title one-line">
          <input type="hidden" id="memberNo"  value="${member.memberNo}">
          <input type="hidden" id="noticeBoardNo" value="${noticeBoardNo}">

          <div class="info-title bg__gray text__black box__l text__center">제목</div>
          <input id="titleVal" type="text" class="info-item bg__white text__black box__l" placeholder="제목을 입력하세요">
        </div>

        <div class="freeBoard-sub one-line">
          <div class="info-title bg__gray text__black box__l text__center">작성자</div>
          <input id="emailVal" type="text" class="info-writer bg__white text__black box__l" value="${member.email}" readonly>

          <div class="info-title bg__gray text__black box__l text__center">작성일</div>
          <input type="text" id="writeDate" class="info-date bg__white text__black box__l" value="${currentDate}" readonly>
        </div>

        <div class="info-content-div reason--title bg__gray text__black box__xl text__center">내용</div>

        <div class="info-content bg__white text__black box__l">
          <textarea id="contentVal" class="contentArea" placeholder="내용을 입력하세요"></textarea>
        </div>
      </div>
    </main>

    <div class="btn-container-all one-line">
      <div class="btn-container">
        <a id="listMove" class="btn btn__generate listMove" href="/member/freeBoard/list?curPage=${curPage}&noticeBoardNo=${noticeBoardNo}&freeBoardSearch=${freeBoardSearch}">
          취소
        </a>
      </div>
      <div class="btn-container">
        <button id="addFreeBoard" class="btn btn__generate listMove text__center text__bold">
          게시글 등록
        </button>
      </div>
    </div>

    <form id="pagingForm" action="./list" method="post">
      <input type="hidden" id="curPage" name="curPage" value="${pagingMap.pagingVo.curPage}" />
    </form>

  </div>
</section>

</body>
<script>

</script>
</html>